package com.code.fry.languages

import com.code.fry.command.Command
import com.code.fry.command.Resource

class Python3Runner(resource: Resource) : Runner(resource) {

    override fun run(): Boolean {
        return Command.execute("python3", getFileAbsPath())

    }

}