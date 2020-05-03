package com.code.fry.languages

import com.code.fry.command.Command
import com.code.fry.command.Resource

class RubyRunner(resource: Resource) : Runner(resource) {
    override fun run(): Boolean {
        return Command.execute("ruby", getFileAbsPath())
    }

}