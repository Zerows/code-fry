package com.code.fry.command


data class Resource(val language: String, val file: String, val content: String, val stdin: Array<String>?)