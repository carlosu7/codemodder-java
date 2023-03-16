package io.codemodder.providers.sarif.semgrep;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.contrastsecurity.sarif.Region;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.codemodder.CodeDirectory;
import io.codemodder.Codemod;
import io.codemodder.CodemodInvocationContext;
import io.codemodder.FileWeavingContext;
import io.codemodder.ReviewGuidance;
import io.codemodder.RuleSarif;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

final class SemgrepJavaParserChangerTest {

  @Codemod(
      author = "pixee",
      id = "pixee-test:java/finds-stuff",
      reviewGuidance = ReviewGuidance.MERGE_AFTER_CURSORY_REVIEW)
  static class FindsStuffCodemod extends SemgrepJavaParserChanger {
    private final RuleSarif ruleSarif;

    @Inject
    FindsStuffCodemod(
        @SemgrepScan(
                pathToYaml = "/other_dir/explicit-yaml-path.yaml",
                ruleId = "explicit-yaml-path")
            RuleSarif ruleSarif) {
      super(ruleSarif);
      this.ruleSarif = ruleSarif;
    }

    @Override
    public ModifierVisitor<FileWeavingContext> createVisitor(
        final CodemodInvocationContext context, final List<Region> regions) {
      return new ModifierVisitor<>();
    }
  }

  @Test
  void it_gives_visitor_when_findings_present(@TempDir Path tmpDir) throws IOException {
    String javaCode = "class Foo { \n\n  Object a = new Stuff(); \n }";
    Path javaFile = writeJavaFile(tmpDir, javaCode);

    SemgrepModule module = new SemgrepModule(tmpDir, List.of(FindsStuffCodemod.class));
    Injector injector = Guice.createInjector(module);
    FindsStuffCodemod instance = injector.getInstance(FindsStuffCodemod.class);
    RuleSarif ruleSarif = instance.ruleSarif;
    assertThat(ruleSarif, is(notNullValue()));
    assertThat(ruleSarif.getRegionsFromResultsByRule(javaFile).size(), is(1));

    CodeDirectory directory = mock(CodeDirectory.class);
    when(directory.asPath()).thenReturn(tmpDir);

    CodemodInvocationContext context = mock(CodemodInvocationContext.class);
    when(context.codemodId()).thenReturn("pixee-test:java/finds-stuff");
    when(context.path()).thenReturn(javaFile);
    when(context.codeDirectory()).thenReturn(directory);
    when(context.changeRecorder()).thenReturn(mock(FileWeavingContext.class));

    // we should get a visitor for this file
    Optional<ModifierVisitor<FileWeavingContext>> modifierVisitor =
        instance.createModifierVisitor(context);
    assertThat(modifierVisitor.isPresent(), is(true));

    // now change it so the path is to a file that should not find results for
    when(context.path()).thenReturn(javaFile.getParent());
    modifierVisitor = instance.createModifierVisitor(context);
    assertThat(modifierVisitor.isEmpty(), is(true));
  }

  private Path writeJavaFile(final Path tmpDir, final String javaCode) throws IOException {
    Path javaFile = Files.createTempFile(tmpDir, "HasStuff", ".java");
    Files.write(
        javaFile, javaCode.getBytes(StandardCharsets.UTF_8), StandardOpenOption.TRUNCATE_EXISTING);
    return javaFile;
  }
}