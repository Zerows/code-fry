package com.code.fry.command


data class Resource(val jobid: String, val language: String, val file: String, val content: String, val stdin: Array<String>?)