plugins {
    id("io.codemodder.java-library")
    id("io.codemodder.maven-publish")
}

description = "Plugin for providing Maven dependency management functions to codemods."

dependencies {
    val docoptVersion = "0.6.0.20150202"
    val commonsLangVersion = "3.12.0"
    val dom4jVersion = "2.1.3"
    val jaxenVersion = "1.2.0"
    val xercesImplVersion = "2.12.2"
    val xmlUnitVersion = "2.9.0"
    val kotlinVersion = "1.7.10"
    val javaSemverVersion = "0.9.0"
    val commonsIoVersion = "2.11.0"
    val guiceVersion = "5.1.0"
    val juniversalchardetVersion = "2.4.0"
    val slf4jSimpleVersion = "2.0.0"
    val diffMatchPatchVersion = "0.0.2"
    val javaDiffUtilsVersion = "4.12"
    val hamcrestVersion = "1.3"
    val junitVersion = "4.13.2"
    val kotlinTestVersion = "1.7.10"
    val slf4jApiVersion = "2.0.0"

    compileOnly(libs.jetbrains.annotations)
    implementation(project(":framework:codemodder-base"))

    testImplementation(testlibs.bundles.junit.jupiter)
    testImplementation(testlibs.bundles.hamcrest)
    testImplementation(testlibs.assertj)
    testImplementation(testlibs.jgit)
    testImplementation(testlibs.mockito)
    testRuntimeOnly(testlibs.junit.jupiter.engine)

    implementation("com.offbytwo:docopt:$docoptVersion")
    implementation("org.apache.commons:commons-lang3:$commonsLangVersion")
    implementation("org.dom4j:dom4j:$dom4jVersion")
    implementation("jaxen:jaxen:$jaxenVersion")
    implementation("xerces:xercesImpl:$xercesImplVersion")
    implementation("org.xmlunit:xmlunit-core:$xmlUnitVersion")
    implementation("org.xmlunit:xmlunit-assertj3:$xmlUnitVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("com.github.zafarkhaja:java-semver:$javaSemverVersion")
    implementation("commons-io:commons-io:$commonsIoVersion")
    implementation("com.google.inject:guice:$guiceVersion")
    implementation("com.github.albfernandez:juniversalchardet:$juniversalchardetVersion")
    testImplementation("org.slf4j:slf4j-simple:$slf4jSimpleVersion")
    testImplementation("fun.mike:diff-match-patch:$diffMatchPatchVersion")
    testImplementation("io.github.java-diff-utils:java-diff-utils:$javaDiffUtilsVersion")
    testImplementation("org.hamcrest:hamcrest-all:$hamcrestVersion")
    testImplementation("junit:junit:$junitVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinTestVersion")
    compileOnly("org.slf4j:slf4j-api:$slf4jApiVersion")
}
