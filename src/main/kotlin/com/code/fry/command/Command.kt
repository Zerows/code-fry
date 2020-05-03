package com.code.fry.command

import com.code.fry.loggers.Logger
import com.code.fry.util.FileUtils
import java.io.File
import java.util.concurrent.TimeUnit

class Command {
    companion object {
        fun execute(vararg commands: String): Boolean {
            Logger.Logger.debug(commands.toString())
            val process = ProcessBuilder(commands.asList())
                    .redirectOutput(ProcessBuilder.Redirect.to(File(FileUtils.OUT_PATH)))
                    .redirectError(ProcessBuilder.Redirect.to(File(FileUtils.ERROR_PATH)))
                    .start()
            if (!process.waitFor(10, TimeUnit.SECONDS)) {
                process.destroy()
                throw RuntimeException("execution timed out: $this")
            }
            return process.exitValue() == 0
        }
    }
}