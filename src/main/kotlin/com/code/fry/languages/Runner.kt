package com.code.fry.languages

import com.code.fry.command.Resource
import com.code.fry.command.Result

abstract class Runner(val resource: Resource) {
    abstract fun run(): Result
    abstract fun ext() : String
}