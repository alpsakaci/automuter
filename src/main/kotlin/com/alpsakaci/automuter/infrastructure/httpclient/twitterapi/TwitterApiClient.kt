package com.alpsakaci.automuter.infrastructure.httpclient.twitterapi

import com.alpsakaci.automuter.application.model.TwitterMetaData
import com.alpsakaci.automuter.application.model.TwitterUser
import com.alpsakaci.automuter.infrastructure.configuration.feign.interceptor.TwitterApiRequestInterceptor
import com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.request.CreateTweetRequest
import com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.request.MuteUserRequest
import com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.resonse.*
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.*

@FeignClient(
    value = "\${feign.client.config.twitter-api.name}",
    url = "\${feign.client.config.twitter-api.url}",
    configuration = [TwitterApiRequestInterceptor::class]
)
interface TwitterApiClient {

    // User Lookup

    @GetMapping("/2/users/me")
    fun me(
        @RequestParam("user.fields") userFields: String,
        @RequestParam("tweet.fields") tweetFields: String
    ): BaseResponse<TwitterUser, TwitterMetaData?>

    @GetMapping("/2/users/{id}")
    fun getUserById(
        @PathVariable("id") id: String,
        @RequestParam("user.fields") userFields: String,
        @RequestParam("tweet.fields") tweetFields: String
    ): BaseResponse<TwitterUser, TwitterMetaData?>

    @GetMapping("/2/users?ids={ids}")
    fun getUsersById(
        @PathVariable("ids") ids: String,
        @RequestParam("user.fields") userFields: String,
        @RequestParam("tweet.fields") tweetFields: String
    ): BaseResponse<ArrayList<TwitterUser>, TwitterMetaData?>

    @GetMapping("/2/users/by/username/{username}")
    fun getUserByUsername(
        @PathVariable("username") username: String,
        @RequestParam("user.fields") userFields: String,
        @RequestParam("tweet.fields") tweetFields: String
    ): BaseResponse<TwitterUser, TwitterMetaData?>

    @GetMapping("/2/users/by?usernames={usernames}")
    fun getUserByUsernames(
        @PathVariable("usernames") usernames: String,
        @RequestParam("user.fields") userFields: String,
        @RequestParam("tweet.fields") tweetFields: String
    ): BaseResponse<ArrayList<TwitterUser>, TwitterMetaData?>

    // Mutes

    @GetMapping("/2/users/{userId}/muting")
    fun mutesLookup(
        @PathVariable("userId") id: String,
        @RequestParam("user.fields") userFields: String,
        @RequestParam("tweet.fields") tweetFields: String,
        @RequestParam("max_results") maxResults: Int,
    ): BaseResponse<ArrayList<TwitterUser>, TwitterMetaData?>

    @GetMapping("/2/users/{userId}/muting")
    fun mutesLookupPaginated(
        @PathVariable("userId") id: String,
        @RequestParam("user.fields") userFields: String,
        @RequestParam("tweet.fields") tweetFields: String,
        @RequestParam("max_results") maxResults: Int,
        @RequestParam("pagination_token") paginationToken: String
    ): BaseResponse<ArrayList<TwitterUser>, TwitterMetaData?>

    @PostMapping("/2/users/{sourceUserId}/muting")
    fun muteUserById(
        @PathVariable("sourceUserId") sourceUserId: String,
        @RequestBody targetUserRequest: MuteUserRequest
    ): BaseResponse<MuteUserResponse, Any>

    @DeleteMapping("/2/users/{source_user_id}/muting/{target_user_id}")
    fun unmuteUserById(
        @PathVariable("source_user_id") source_user_id: String,
        @PathVariable("target_user_id") target_user_id: String
    ): BaseResponse<MuteUserResponse, Any>

    // Follows

    @GetMapping("/2/users/{id}/followers")
    fun getFollowers(
        @PathVariable("id") id: String,
        @RequestParam("user.fields") userFields: String,
        @RequestParam("tweet.fields") tweetFields: String,
        @RequestParam("max_results") maxResults: Int,
    ): BaseResponse<ArrayList<TwitterUser>, TwitterMetaData?>

    @GetMapping("/2/users/{id}/followers")
    fun getFollowersPaginated(
        @PathVariable("id") id: String,
        @RequestParam("user.fields") userFields: String,
        @RequestParam("tweet.fields") tweetFields: String,
        @RequestParam("max_results") maxResults: Int,
        @RequestParam("pagination_token") paginationToken: String
    ): BaseResponse<ArrayList<TwitterUser>, TwitterMetaData?>

    @GetMapping("/2/users/{id}/following")
    fun getFollowing(
        @PathVariable("id") id: String,
        @RequestParam("user.fields") userFields: String,
        @RequestParam("tweet.fields") tweetFields: String,
        @RequestParam("max_results") maxResults: Int,
    ): BaseResponse<ArrayList<TwitterUser>, TwitterMetaData?>

    @GetMapping("/2/users/{id}/following")
    fun getFollowingPaginated(
        @PathVariable("id") id: String,
        @RequestParam("user.fields") userFields: String,
        @RequestParam("tweet.fields") tweetFields: String,
        @RequestParam("max_results") maxResults: Int,
        @RequestParam("pagination_token") paginationToken: String
    ): BaseResponse<ArrayList<TwitterUser>, TwitterMetaData?>


    // Manage Tweets

    @PostMapping("/2/tweets")
    fun createTweet(@RequestBody createTweetRequest: CreateTweetRequest): BaseResponse<CreateTweetResponse, TwitterMetaData?>
}
