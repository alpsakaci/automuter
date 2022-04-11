package com.alpsakaci.automuter.infrastructure.controller

import com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.TwitterApiClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(val twitterApiClient: TwitterApiClient)  {

    val userFields = "description,profile_image_url"
    val tweetFields = "attachments,lang,entities,referenced_tweets,text"

    @GetMapping("/me")
    fun me(): Any {
        return twitterApiClient.me(userFields, tweetFields)
    }

    @GetMapping("/get-by-id/{userId}")
    fun getUserById(@PathVariable("userId") userId: String): Any {
        return twitterApiClient.getUserById(userId, userFields, tweetFields)
    }

    @GetMapping("/get-by-ids/{ids}")
    fun getUsersById(@PathVariable("ids") ids: String): Any {
        return twitterApiClient.getUsersById(ids, userFields, tweetFields)
    }

    @GetMapping("/get-by-username/{username}")
    fun getUserByUsername(@PathVariable("username") username: String): Any {
        return twitterApiClient.getUserByUsername(username, userFields, tweetFields)
    }

    @GetMapping("/get-by-usernames/{usernames}")
    fun getUserByUsernames(@PathVariable("usernames") usernames: String): Any {
        return twitterApiClient.getUserByUsernames(usernames, userFields, tweetFields)
    }
}
