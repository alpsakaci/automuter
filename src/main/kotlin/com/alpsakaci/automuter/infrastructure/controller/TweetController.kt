package com.alpsakaci.automuter.infrastructure.controller

import com.alpsakaci.automuter.application.command.MuteUserCommand
import com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.TwitterApiClient
import com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.request.CreateTweetRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tweets")
class TweetController(
    val twitterApiClient: TwitterApiClient
){

    @PostMapping("/create-tweet")
    fun muteUserById(@RequestBody createTweetRequest: CreateTweetRequest): Any {
        return twitterApiClient.createTweet(createTweetRequest)
    }
}
