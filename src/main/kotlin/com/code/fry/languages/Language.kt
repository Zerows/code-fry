package com.code.fry.languages

import com.code.fry.command.Resource
import com.code.fry.command.Result


class Language {
    companion object {
        fun isSupported() : Boolean {
            return true
        }
        fun run(language: String, content: Resource): Result {
            val runner = Runners.getRunner(language, content)
            if (runner != null){
                runner.run()
            }else {
                throw IllegalArgumentException("Unsupported Lauguage")
            }
            return Result()
        }
    }
}