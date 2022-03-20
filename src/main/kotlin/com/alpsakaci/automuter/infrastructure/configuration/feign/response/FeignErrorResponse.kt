package com.alpsakaci.automuter.infrastructure.configuration.feign.response

import com.google.gson.Gson

data class FeignErrorResponse(val status: Int, val detail: String) {
    fun toJson(): String {
        return Gson().toJson(this)
    }
}
