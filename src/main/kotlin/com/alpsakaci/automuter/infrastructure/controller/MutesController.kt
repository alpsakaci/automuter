package com.alpsakaci.automuter.infrastructure.controller

import com.alpsakaci.automuter.application.command.*
import com.alpsakaci.automuter.application.query.MutesLookupQuery
import com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.TwitterApiClient
import com.trendyol.kediatr.CommandBus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/mutes")
class MutesController(
    val twitterApiClient: TwitterApiClient,
    val commandBus: CommandBus
) {

    val userFields = "description,profile_image_url"
    val tweetFields = "attachments,lang,entities,referenced_tweets,text"

    @GetMapping("/lookup")
    fun mutesLookup(@RequestParam("pagination_token", required = false) paginationToken: String?): Any {
        val command = MutesLookupQuery(paginationToken)
        return commandBus.executeQuery(command)
    }

    @PostMapping("/mute/{targetUserId}")
    fun muteUserById(@PathVariable("targetUserId") targetUserId: String) {
        val command = MuteUserCommand(targetUserId)
        commandBus.executeCommand(command)
    }

    @PostMapping("/mute-by-username/{targetUsername}")
    fun muteUserByUsername(@PathVariable("targetUsername") targetUsername: String) {
        val command = MuteUserByUsernameCommand(targetUsername)
        commandBus.executeCommand(command)
    }

    @PostMapping("/mute-all-followers/{targetUsername}")
    fun muteAllFollowers(@PathVariable("targetUsername") targetUsername: String) {
        val command = MuteAllFollowersByUsernameCommand(targetUsername)
        commandBus.executeCommand(command)
    }

    @DeleteMapping("/unmute/{targetUserId}")
    fun unmuteUserById(@PathVariable("targetUserId") targetUserId: String) {
        val command = UnMuteUserCommand(targetUserId)
        commandBus.executeCommand(command)
    }

    @DeleteMapping("/unmute-by-username/{targetUsername}")
    fun unmuteUserByUsername(@PathVariable("targetUsername") targetUsername: String) {
        val command = UnMuteUserByUsernameCommand(targetUsername)
        commandBus.executeCommand(command)
    }
}
