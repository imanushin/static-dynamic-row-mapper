package com.github.imanushin

import org.jetbrains.kotlin.cli.common.environment.setIdeaIoUseFallback
import org.jetbrains.kotlin.script.jsr223.KotlinJsr223JvmLocalScriptEngine
import org.springframework.stereotype.Service
import javax.script.ScriptEngineManager

internal interface KotlinClassCompilation {
    fun <TResult> compile(sourceCode: String): TResult
}

@Service
internal class KotlinClassCompilationImpl : KotlinClassCompilation {

    private val scriptEngine = {
        // see https://discuss.kotlinlang.org/t/kotlin-script-engine-error/5654/2
        setIdeaIoUseFallback()

        ScriptEngineManager()
    }()

    override fun <TResult> compile(sourceCode: String): TResult {
        val factory = scriptEngine.getEngineByExtension("kts").factory

        val engine = factory.scriptEngine as KotlinJsr223JvmLocalScriptEngine

        @Suppress("UNCHECKED_CAST")
        return engine.eval(sourceCode) as TResult
    }

}
