package com.alpsakaci.automuter.infrastructure.controller

import com.alpsakaci.automuter.application.query.FindAllFollowersQuery
import com.alpsakaci.automuter.application.query.FindAllFollowingQuery
import com.alpsakaci.automuter.application.query.FindUnfollowersQuery
import com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.TwitterApiClient
import com.trendyol.kediatr.CommandBus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/follow")
class FollowController(
    val twitterApiClient: TwitterApiClient,
    val commandBus: CommandBus
) {

    val userFields = "description,profile_image_url"
    val tweetFields = "attachments,lang,entities,referenced_tweets,text"

    @GetMapping("/get-all-following")
    fun getAllFollowing(): Any {
        val userId = twitterApiClient.me(userFields,tweetFields).data?.id
        val query = FindAllFollowingQuery(userId!!)
        return commandBus.executeQuery(query)
    }

    @GetMapping("/get-all-followers")
    fun getAllFollowers(): Any {
        val userId = twitterApiClient.me(userFields,tweetFields).data?.id
        val query = FindAllFollowersQuery(userId!!)
        return commandBus.executeQuery(query)
    }

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

    @GetMapping("/find-unfollowers")
    fun findUnfollowers(): Any {
        val userId = twitterApiClient.me(userFields,tweetFields).data?.id
        val query = FindUnfollowersQuery(userId!!)
        return commandBus.executeQuery(query)
    }
}
