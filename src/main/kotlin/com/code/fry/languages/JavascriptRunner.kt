package com.code.fry.languages

import com.code.fry.command.Command
import com.code.fry.command.Resource

class JavascriptRunner(resource: Resource) : Runner(resource) {
    override fun run(): Boolean {
        return Command.execute("node", getFileAbsPath())
    }

}