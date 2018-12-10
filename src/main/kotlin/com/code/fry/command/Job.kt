package com.code.fry.command

import com.google.gson.annotations.SerializedName

class Job(val id: String, @SerializedName("result_id") val resultId: Int)