package com.code.fry.languages

import com.code.fry.command.Resource
import org.jetbrains.annotations.NotNull

class Runners {
    companion object {
        const val JAVA: String = "java"
        const val PYTHON: String = "python"
        const val JAVASCRIPT: String = "javascript"
        const val RUBY: String = "ruby"

         fun getRunner(@NotNull language: String, @NotNull resource: Resource) : Runner?{
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
    }
}