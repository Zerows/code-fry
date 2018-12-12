package com.code.fry.languages

import com.code.fry.command.Output
import com.code.fry.command.Resource
import org.jetbrains.annotations.NotNull

class Runners {
    companion object {
        const val JAVA: String = "java"
        const val PYTHON: String = "python"
        const val JAVASCRIPT: String = "javascript"
        const val RUBY: String = "ruby"

         private fun getRunner(@NotNull language: String, @NotNull resource: Resource) : Runner?{
             return when(language.toLowerCase()){
                 JAVA -> {
                     JavaRunner(resource)
                 }
                 PYTHON -> {
                     PythonRunner(resource)
                 }
                 JAVASCRIPT -> {
                     JavascriptRunner(resource)
                 }
                 RUBY -> {
                     RubyRunner(resource)
                 }
                 else -> {
                     null
                 }
             }
        }
        fun run(language: String, content: Resource): Output? {
            val runner = getRunner(language, content)
            if (runner != null) {
                try {
                    runner.createFiles()
                    runner.run()
                    val result = runner.collectOutput()
                    runner.cleanup()
                    return result
                } catch (e: RuntimeException) {
                    throw e
                } finally {

                }
            } else {
                throw IllegalArgumentException("Unsupported Lauguage")
            }
        }
    }
}