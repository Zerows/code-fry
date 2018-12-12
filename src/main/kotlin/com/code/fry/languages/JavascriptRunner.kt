package com.code.fry.languages

import com.code.fry.command.Command
import com.code.fry.command.Resource
import com.code.fry.util.FileUtils.Companion.TMP_DIR

class JavascriptRunner(resource: Resource) : Runner(resource) {

    override fun ext(): String {
        return "js"
    }

    override fun run(): Boolean {
        return Command.execute("node", getFileAbsPath())
    }

}