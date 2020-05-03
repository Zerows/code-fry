package com.code.fry.languages

import com.code.fry.command.Command
import com.code.fry.command.Resource
import java.io.File

class PythonRunner(resource: Resource) : Runner(resource) {

    override fun run(): Boolean {
        val file = File(resource.file)
        return Command.execute("python", getFileAbsPath())

    }

}