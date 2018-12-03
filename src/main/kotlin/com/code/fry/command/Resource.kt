package com.code.fry.command


data class Resource(var jobid: String, val language: String, val file: String, val content: String, val stdin: Array<String>?)