package com.alpsakaci.automuter.infrastructure.httpclient.githubapi.response

data class GithubGetUserResponse(
    val login: String,
    val id: Long,
    val avatar_url: String?,
    val gravatar_id: String?,
    val url: String,
    val html_url: String,
    val name: String?,
    val company: String?,
    val blog: String?,
    val location: String?,
    val bio: String?,
    val email: String?,
    val twitter_username: String?,
)
