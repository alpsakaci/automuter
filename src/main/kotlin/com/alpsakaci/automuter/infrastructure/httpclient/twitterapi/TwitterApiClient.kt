package com.alpsakaci.automuter.infrastructure.httpclient.twitterapi

import com.alpsakaci.automuter.infrastructure.configuration.feign.interceptor.TwitterApiRequestInterceptor
import com.alpsakaci.automuter.infrastructure.controller.MuteUserRequest
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
    fun me(): Any

    @GetMapping("/2/users/{id}")
    fun getUserById(@PathVariable("id") id: String): Any

    @GetMapping("/2/users?ids={ids}")
    fun getUsersById(@PathVariable("ids") ids: String): Any

    @GetMapping("/2/users/by/username/{username}")
    fun getUserByUsername(@PathVariable("username") username: String): Any

    @GetMapping("/2/users/by?usernames={usernames}")
    fun getUserByUsernames(@PathVariable("usernames") usernames: String): Any

    // Mutes

    @GetMapping("/2/users/{id}/muting")
    fun mutesLookup(@PathVariable("id") id: String): Any

    @PostMapping("/2/users/{id}/muting")
    fun muteUserById(@PathVariable("id") id: String, @RequestBody targetUser: MuteUserRequest): Any

    @DeleteMapping("/2/users/{source_user_id}/muting/{target_user_id}")
    fun unmuteUserById(@PathVariable("source_user_id") source_user_id: String, @PathVariable("target_user_id") target_user_id: String): Any

}
