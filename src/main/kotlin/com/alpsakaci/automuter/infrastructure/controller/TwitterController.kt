package com.alpsakaci.automuter.infrastructure.controller

import com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.TwitterApiClient
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/twitter")
class TwitterController(val twitterApiClient: TwitterApiClient) {

    // User Lookup

    @GetMapping("/users-me")
    fun me(): Any {
        return twitterApiClient.me()
    }

    @GetMapping("/users-by-id/{userId}")
    fun getUserById(@PathVariable("userId") userId: String): Any {
       return twitterApiClient.getUserById(userId)
    }

    @GetMapping("/users-by-ids/{ids}")
    fun getUsersById(@PathVariable("ids") ids: String): Any {
        return twitterApiClient.getUsersById(ids)
    }

    @GetMapping("/users-by-username/{username}")
    fun getUserByUsername(@PathVariable("username") username: String): Any {
        return twitterApiClient.getUserByUsername(username)
    }

    @GetMapping("/users-by-usernames/{usernames}")
    fun getUserByUsernames(@PathVariable("usernames") usernames: String): Any {
        return twitterApiClient.getUserByUsernames(usernames)
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
}

data class MuteUserRequest(
    val target_user_id: String
)
