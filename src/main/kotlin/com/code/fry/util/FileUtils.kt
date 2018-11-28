package com.code.fry.util

import java.io.File
import java.io.FileNotFoundException

class FileUtils {
    companion object {
        fun write(path: String, content: String){
            File(path).bufferedWriter().use { out -> out.write(content) }
        }
        fun read(path: String): String{
            val f = File(path)
            if(f.exists()) {
                return File(path).inputStream().readBytes().toString(Charsets.UTF_8)
            }else{
                throw FileNotFoundException("No this file is not found FilePath: $path")
            }
        }
        fun delete(path: String): Boolean{
            return File(path).delete()
        }

    }
}