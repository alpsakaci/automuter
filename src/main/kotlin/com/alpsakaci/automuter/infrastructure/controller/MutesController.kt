package com.alpsakaci.automuter.infrastructure.controller

import com.alpsakaci.automuter.application.command.*
import com.alpsakaci.automuter.application.query.MutesLookupQuery
import com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.TwitterApiClient
import com.trendyol.kediatr.CommandBus
import kotlinx.coroutines.runBlocking
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
        val userId = twitterApiClient.me(userFields, tweetFields).data!!.id
        paginationToken?.let {
            return twitterApiClient.mutesLookupPaginated(userId, userFields, tweetFields, 1000, paginationToken)
        }

        return twitterApiClient.mutesLookup(userId, userFields, tweetFields, 1000)
    }

    @GetMapping("/get-all-muted-accounts")
    fun getAllMutedAccounts(): Any {
        val command = MutesLookupQuery(1000)
        return commandBus.executeQuery(command)
    }

    @PostMapping("/mute")
    fun muteUserById(@RequestBody command: MuteUserCommand) {
        commandBus.executeCommand(command)
    }

    @PostMapping("/mute-by-username")
    fun muteUserByUsername(@RequestBody command: MuteUserByUsernameCommand) {
        commandBus.executeCommand(command)
    }

    @PostMapping("/mute-all-followers")
    fun muteAllFollowers(@RequestBody command: MuteAllFollowersByUsernameCommand){
        runBlocking { commandBus.executeCommandAsync(command) }
    }

    @DeleteMapping("/unmute")
    fun unmuteUserById(@RequestBody command: UnMuteUserCommand) {
        commandBus.executeCommand(command)
    }

    @DeleteMapping("/unmute-by-username")
    fun unmuteUserByUsername(@RequestBody command: UnMuteUserByUsernameCommand) {
        commandBus.executeCommand(command)
    }
}
