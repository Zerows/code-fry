package com.code.fry.languages

import com.code.fry.command.Resource

abstract class Runner(val resource: Resource) {
    abstract fun run()
    abstract fun ext() : String
}