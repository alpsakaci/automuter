package com.alpsakaci.automuter.infrastructure.controller

import com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.TwitterApiClient
import com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.request.MuteUserRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/follow")
class FollowController(val twitterApiClient: TwitterApiClient) {

    val userFields = "description,profile_image_url"
    val tweetFields = "attachments,lang,entities,referenced_tweets,text"

    @GetMapping("/get-followers/{userId}")
    fun getFollowers(@PathVariable("userId") userId: String, @RequestParam("pagination_token", required = false) paginationToken: String?): Any {
        if (paginationToken != null) {
            return twitterApiClient.getFollowersPaginated(userId, userFields, tweetFields, 100, paginationToken)
        }
        return twitterApiClient.getFollowers(userId, userFields, tweetFields, 100)
    }

    @GetMapping("/get-following/{userId}")
    fun getFollowing(@PathVariable("userId") userId: String, @RequestParam("pagination_token", required = false) paginationToken: String?): Any {
        if (paginationToken != null) {
            return twitterApiClient.getFollowingPaginated(userId, userFields, tweetFields, 100, paginationToken)
        }
        return twitterApiClient.getFollowing(userId, userFields, tweetFields, 100)
    }
}
