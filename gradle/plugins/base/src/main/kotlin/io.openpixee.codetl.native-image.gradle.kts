plugins {
    application
    id("org.graalvm.buildtools.native")
}

tasks.named(LifecycleBasePlugin.ASSEMBLE_TASK_NAME) {
    dependsOn(tasks.nativeCompile)
}
