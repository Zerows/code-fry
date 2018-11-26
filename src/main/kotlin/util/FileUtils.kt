package util

import java.io.File

class FileUtils {
    companion object {
        fun write(path: String, content: String){
            File(path).bufferedWriter().use { out -> out.write(content) }
        }
        fun read(path: String): String{
            return File(path).inputStream().readBytes().toString(Charsets.UTF_8)
        }

    }
}