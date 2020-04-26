package com.github.imanushin.test

import com.github.imanushin.PluginConstants
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.gradle.internal.impldep.org.junit.rules.TemporaryFolder
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome

class PluginEvaluationTest : FreeSpec() {
    init {
        "run project with plugin" {
            val testProjectDir = TemporaryFolder()
            testProjectDir.create()
            val buildFile = testProjectDir.newFile("build.gradle")
            buildFile.writeText(
                    """
                        plugins {
                            id("my-plugin")
                        }
                    """.trimIndent()
            )

            val result = GradleRunner.create()
                    .withProjectDir(testProjectDir.root)
                    .withArguments(PluginConstants.taskName)
                    .withPluginClasspath()
                    .build()

            result.output.contains(PluginConstants.pluginOutputText)
            result.task(":${PluginConstants.taskName}")?.outcome shouldBe TaskOutcome.SUCCESS
        }
    }
}