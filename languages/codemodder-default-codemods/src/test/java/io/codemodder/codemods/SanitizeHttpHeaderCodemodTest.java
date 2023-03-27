package io.codemodder.codemods;

import io.codemodder.testutils.CodemodTestMixin;
import io.codemodder.testutils.Metadata;

@Metadata(
    codemodType = SanitizeHttpHeaderCodemod.class,
    testResourceDir = "strip-http-header-newlines",
    dependencies = "io.openpixee:java-security-toolkit:1.0.0")
final class SanitizeHttpHeaderCodemodTest implements CodemodTestMixin {}
