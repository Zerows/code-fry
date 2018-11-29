package com.code.fry.util

import java.io.File
import java.io.FileNotFoundException

class FileUtils {
    companion object {
        const val TMP_DIR: String = "tmp"
        const val OUT_DIR: String = "output"
        const val OUT_PATH: String = "$OUT_DIR/output.txt"
        const val ERROR_PATH: String = "$OUT_DIR/error.txt"

        fun createTmp() {
            val file = File(TMP_DIR)
            file.mkdir()
        }

        fun createOutput() {
            val file = File(OUT_DIR)
            file.mkdir()
        }
        fun write(path: String, content: String): File {
            val file = File(path)
            file.writeText(content)
            return file
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

        fun cleanup(): Boolean {
            File(TMP_DIR).deleteRecursively()
            File(OUT_DIR).deleteRecursively()
            return true
        }

    }
}