package com.code.fry.languages

import com.code.fry.command.Command
import com.code.fry.command.Resource
import com.code.fry.util.FileUtils.Companion.TMP_DIR

class RubyRunner(resource: Resource) : Runner(resource) {

    override fun ext(): String {
        return "rb"
    }

    override fun run(): Boolean {
        return Command.execute("ruby", "$TMP_DIR/${resource.file}")
    }

}