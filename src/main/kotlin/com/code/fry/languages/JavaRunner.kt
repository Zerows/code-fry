package com.code.fry.languages

import com.code.fry.command.Command
import com.code.fry.command.Resource
import com.code.fry.command.Result
import com.code.fry.util.FileUtils
import java.io.File

class JavaRunner(resource: Resource) : Runner(resource) {

    override fun ext(): String {
        return "java"
    }

    override fun run(): Result {
        println("Runing Java ${resource.file}")
        val file = FileUtils.write(resource.file, resource.content)
        println("Compiling java program")
        Command.execute("javac", resource.file)
        println("Starting To Run java program")
        val runResult = Command.execute("java", file.nameWithoutExtension)
        println("End Running java program")
        FileUtils.delete(file.absolutePath)
        FileUtils.delete(getFileWithClassExtension(file))
        return runResult
    }

    private fun getFileWithClassExtension(file: File): String {
        return "${file.nameWithoutExtension}.class"
    }

}