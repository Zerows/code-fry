package com.code.fry.languages

import com.code.fry.command.Command
import com.code.fry.command.Resource
import com.code.fry.util.FileUtils.Companion.TMP_DIR

class PythonRunner(resource: Resource) : Runner(resource) {

    override fun ext(): String {
        return "py"
    }

    override fun run(): Boolean {
        return Command.execute("python", "$TMP_DIR/${resource.file}")

    }

}