package com.alpsakaci.automuter.infrastructure.httpclient.twitterapi

import com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.resonse.UserResponse
import org.springframework.web.bind.annotation.GetMapping

interface TwitterApiClient {

    @GetMapping
    fun getUser(): UserResponse
}
