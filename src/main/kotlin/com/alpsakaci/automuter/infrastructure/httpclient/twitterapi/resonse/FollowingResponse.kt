package com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.resonse

data class FollowingResponse(
    val data: ArrayList<UserResponse>,
    val meta: MetaDataResponse?
)
