package languages

import command.Resource

class JavaRunner(resource: Resource) : Runner(resource) {

    override fun ext(): String {
        return "java"
    }

    override fun run() {
        println("Runing Java ${resource.file}")
    }

}