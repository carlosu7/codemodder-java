plugins {
    id("io.codemodder.base")
    id("io.codemodder.java-library")
    id("io.codemodder.maven-publish")
}

dependencies {
    compileOnly(libs.jetbrains.annotations)
    implementation("io.codemodder:codemodder-core")

    testImplementation(testlibs.bundles.junit.jupiter)
    testImplementation(testlibs.bundles.hamcrest)
    testImplementation(testlibs.assertj)
    testImplementation(testlibs.jgit)
    testImplementation(testlibs.mockito)
    testRuntimeOnly(testlibs.junit.jupiter.engine)
}
