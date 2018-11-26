import com.google.gson.Gson
import command.Resource
import languages.Language
import org.apache.commons.cli.Options
import org.apache.commons.cli.DefaultParser
import util.FileUtils


class Main {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            // create Options object
            val options = Options()
            options.addOption("c", "content", true, "Enter a valid json content")
            val parser = DefaultParser()
            val cmd = parser.parse(options, args)
            try {
                val path = System.getProperty("user.dir")
                val content = FileUtils.read("${path}/${cmd.getOptionValue("content")}")
                val resource = Gson().fromJson(content, Resource::class.java)
                Language.run("java", resource)
                //Language.run("kava", "")
            } catch (e: IllegalArgumentException) {
                println(e)
            }
        }
    }
}