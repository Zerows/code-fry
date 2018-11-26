package languages

import command.Resource
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

class Runners {
    companion object {
         fun getRunner(@NotNull language: String, @NotNull resource: Resource) : Runner?{
             return when(language.toLowerCase()){
                 "java" -> {
                     JavaRunner(resource)
                 }
                 else -> {
                     null
                 }
             }
        }
    }
}