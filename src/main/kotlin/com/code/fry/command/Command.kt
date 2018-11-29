package com.code.fry.command

import java.util.concurrent.TimeUnit

class Command {
    companion object {
        fun execute(vararg commands: String): Result {
            val process = ProcessBuilder(commands.asList())
                    .redirectOutput(ProcessBuilder.Redirect.PIPE)
                    .redirectError(ProcessBuilder.Redirect.PIPE)
                    .start()
            if (!process.waitFor(10, TimeUnit.SECONDS)) {
                process.destroy()
                throw RuntimeException("execution timed out: $this")
            }
            if (process.exitValue() != 0) {
                throw RuntimeException("execution failed with code ${process.exitValue()}: $this")
            } else {
                val output = process.inputStream.bufferedReader(Charsets.UTF_8).readText()
                return Result(output, *commands)
            }
        }
    }
}