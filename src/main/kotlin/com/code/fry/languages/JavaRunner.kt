package com.code.fry.languages

import com.code.fry.command.Resource
import com.code.fry.util.FileUtils

class JavaRunner(resource: Resource) : Runner(resource) {

    override fun ext(): String {
        return "java"
    }

    override fun run() {
        println("Runing Java ${resource.file}")
        FileUtils.write(resource.file, resource.content)
        var process = ProcessBuilder("javac", resource.file)
                .redirectOutput(ProcessBuilder.Redirect.INHERIT)
                .redirectError(ProcessBuilder.Redirect.INHERIT)
                .start()

        println(process.inputStream.bufferedReader(Charsets.UTF_8).readText())
        println("Starting TO Run java program")
        process = ProcessBuilder("java", "HelloWorld")
                .redirectOutput(ProcessBuilder.Redirect.INHERIT)
                .redirectError(ProcessBuilder.Redirect.INHERIT)
                .start()
        println(process.inputStream.bufferedReader(Charsets.UTF_8).readText())


        println("End Running java program")
        //FileUtils.delete(resource.file)
        //FileUtils.delete("HelloWorld.class")
    }

}