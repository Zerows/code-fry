package com.code.fry.util

import com.code.fry.languages.Runners

@SuppressWarnings("Matching the enums to lower cases")
enum class FileName(val customName: String) {
    java("Solution.java"),
    python("solution.py"),
    javascript("solution.js"),
    ruby("solution.rb")
}