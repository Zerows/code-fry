package com.code.fry.languages

import com.code.fry.command.Command
import com.code.fry.command.Resource
import com.code.fry.util.FileUtils.Companion.TMP_DIR
import java.io.File

class JavaRunner(resource: Resource) : Runner(resource) {

    override fun run(): Boolean {
        val file = File(resource.file)
        val compilationResult = Command.execute("javac", getFileAbsPath())
        return if (compilationResult) {
            return Command.execute("java", "-cp", TMP_DIR, file.nameWithoutExtension)
        } else {
            compilationResult
        }
    }

}