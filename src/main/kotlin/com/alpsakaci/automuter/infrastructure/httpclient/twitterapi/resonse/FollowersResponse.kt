package com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.resonse

data class FollowersResponse(
    val data: ArrayList<UserResponse>,
    val meta: MetaDataResponse?
)
