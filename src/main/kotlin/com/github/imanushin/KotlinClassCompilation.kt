package com.github.imanushin

import org.jetbrains.kotlin.script.jsr223.KotlinJsr223JvmLocalScriptEngine
import org.springframework.stereotype.Service
import javax.script.ScriptEngineManager

internal interface KotlinClassCompilation {
    fun <TResult> compile(sourceCode: String): TResult
}

@Service
internal class KotlinClassCompilationImpl : KotlinClassCompilation{

    override fun <TResult> compile(sourceCode: String): TResult {
        val factory = ScriptEngineManager().getEngineByExtension("kts").factory

        val engine = factory.scriptEngine as KotlinJsr223JvmLocalScriptEngine

        @Suppress("UNCHECKED_CAST")
        return engine.eval(sourceCode) as TResult
    }

}