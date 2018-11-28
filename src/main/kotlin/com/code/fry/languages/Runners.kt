package com.code.fry.languages

import com.code.fry.command.Resource
import org.jetbrains.annotations.NotNull

class Runners {
    companion object {
         fun getRunner(@NotNull language: String, @NotNull resource: Resource) : Runner?{
             return when(language.toLowerCase()){
                 "java" -> {
                     JavaRunner(resource)
                 }
                 else -> {
                     null
                 }
             }
        }
    }
}