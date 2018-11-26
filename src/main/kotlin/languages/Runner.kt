package languages

import command.Resource

abstract class Runner(val resource: Resource) {
    abstract fun run()
    abstract fun ext() : String
}