package com.code.fry.languages

import com.code.fry.command.Output
import com.code.fry.command.Resource
import com.code.fry.loggers.Logger
import com.code.fry.util.FileUtils

abstract class Runner(val resource: Resource) {
    abstract fun run(): Boolean
    fun createFiles() {
        FileUtils.createTmp()
        FileUtils.createOutput()
        FileUtils.write(getFileAbsPath(), resource.content)
        Logger.Logger.info("Wrote file, ${getFileAbsPath()}")
    }

    fun collectOutput(): Output {
        val output = FileUtils.read(FileUtils.OUT_PATH)
        val error = FileUtils.read(FileUtils.ERROR_PATH)
        return Output(output, error, resource.jobid)
    }

    fun cleanup() {
        FileUtils.cleanup()
    }

    fun getFileAbsPath(): String {
        return "${FileUtils.TMP_DIR}/${resource.file}"
    }


}