package com.code.fry.languages

import com.code.fry.command.Output
import com.code.fry.command.Resource


class Language {
    companion object {
        fun run(language: String, content: Resource): Output? {
            val runner = Runners.getRunner(language, content)
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