package com.alpsakaci.automuter.infrastructure.controller

import com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.TwitterApiClient
import com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.request.MuteUserRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/twitter")
class TwitterController(val twitterApiClient: TwitterApiClient) {

    val userFields = "description,profile_image_url"
    val tweetFields = "attachments,lang,entities,referenced_tweets,text"

    // User Lookup

    @GetMapping("/users-me")
    fun me(): Any {
        return twitterApiClient.me()
    }

    @GetMapping("/users-by-id/{userId}")
    fun getUserById(@PathVariable("userId") userId: String): Any {
        return twitterApiClient.getUserById(userId, userFields, tweetFields)
    }

    @GetMapping("/users-by-ids/{ids}")
    fun getUsersById(@PathVariable("ids") ids: String): Any {
        return twitterApiClient.getUsersById(ids, userFields, tweetFields)
    }

    @GetMapping("/users-by-username/{username}")
    fun getUserByUsername(@PathVariable("username") username: String): Any {
        return twitterApiClient.getUserByUsername(username, userFields, tweetFields)
    }

    @GetMapping("/users-by-usernames/{usernames}")
    fun getUserByUsernames(@PathVariable("usernames") usernames: String): Any {
        return twitterApiClient.getUserByUsernames(usernames, userFields, tweetFields)
    }

    // Mutes

    @GetMapping("/mutes/lookup/{userId}")
    fun mutesLookup(@PathVariable("userId") userId: String): Any {
        return twitterApiClient.mutesLookup(userId)
    }

    @PostMapping("/mutes/mute/{sourceUserId}/{targetUserId}")
    fun muteUserById(@PathVariable("sourceUserId") sourceUserId: String, @PathVariable("targetUserId") targetUserId: String): Any {
        val targetUser = MuteUserRequest(targetUserId)
        return twitterApiClient.muteUserById(sourceUserId, targetUser)
    }

    @DeleteMapping("/mutes/umute/{sourceUserId}/{targetUserId}")
    fun unmuteUserById(@PathVariable("sourceUserId") sourceUserId: String, @PathVariable("targetUserId") targetUserId: String): Any {
        return twitterApiClient.unmuteUserById(sourceUserId, targetUserId)
    }

    // Follows

    @GetMapping("/2/users/{userId}/followers")
    fun getFollowers(@PathVariable("userId") userId: String, @RequestParam("pagination_token", required = false) paginationToken: String?): Any {
        if (paginationToken != null) {
            return twitterApiClient.getFollowersPaginated(userId, userFields, tweetFields, 100, paginationToken)
        }
        return twitterApiClient.getFollowers(userId, userFields, tweetFields, 100)
    }

    @GetMapping("/2/users/{userId}/following")
    fun getFollowing(@PathVariable("userId") userId: String, @RequestParam("pagination_token", required = false) paginationToken: String?): Any {
        if (paginationToken != null) {
            return twitterApiClient.getFollowingPaginated(userId, userFields, tweetFields, 100, paginationToken)
        }
        return twitterApiClient.getFollowing(userId, userFields, tweetFields, 100)
    }

}

