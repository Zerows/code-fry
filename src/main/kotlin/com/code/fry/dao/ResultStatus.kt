package com.code.fry.dao

enum class ResultStatus(val status: Int){
    InQueue(0),
    InProgress(1),
    Completed(2),
    Cancelled(3)
}