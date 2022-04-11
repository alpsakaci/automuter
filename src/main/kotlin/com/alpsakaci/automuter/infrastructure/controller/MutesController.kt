package com.alpsakaci.automuter.infrastructure.controller

import com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.TwitterApiClient
import com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.request.MuteUserRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/mutes")
class MutesController(val twitterApiClient: TwitterApiClient) {

    val userFields = "description,profile_image_url"
    val tweetFields = "attachments,lang,entities,referenced_tweets,text"

    @GetMapping("/lookup/{userId}")
    fun mutesLookup(@PathVariable("userId") userId: String, @RequestParam("pagination_token", required = false) paginationToken: String?): Any {
        if (paginationToken != null) {
            return twitterApiClient.mutesLookupPaginated(userId, userFields, tweetFields, 100, paginationToken)
        }
        return twitterApiClient.mutesLookup(userId, userFields, tweetFields, 100)
    }

    @PostMapping("/mute/{sourceUserId}/{targetUserId}")
    fun muteUserById(@PathVariable("sourceUserId") sourceUserId: String, @PathVariable("targetUserId") targetUserId: String): Any {
        val targetUser = MuteUserRequest(targetUserId)
        return twitterApiClient.muteUserById(sourceUserId, targetUser)
    }

    @DeleteMapping("/unmute/{sourceUserId}/{targetUserId}")
    fun unmuteUserById(@PathVariable("sourceUserId") sourceUserId: String, @PathVariable("targetUserId") targetUserId: String): Any {
        return twitterApiClient.unmuteUserById(sourceUserId, targetUserId)
    }
}
