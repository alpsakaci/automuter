package com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.resonse

data class UserResponse(
    val username: String,
    val id: String,
    val profile_image_url: String,
    val name: String,
    val description: String
)
