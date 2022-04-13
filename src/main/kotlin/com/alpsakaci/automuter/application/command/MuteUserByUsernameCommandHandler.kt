package com.alpsakaci.automuter.application.command

import com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.TwitterApiClient
import com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.request.MuteUserRequest
import com.trendyol.kediatr.CommandHandler
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component

@Component
@Lazy
class MuteUserByUsernameCommandHandler(
    val twitterApiClient: TwitterApiClient
) : CommandHandler<MuteUserByUsernameCommand> {

    val userFields = "description,profile_image_url"
    val tweetFields = "entities"

    override fun handle(command: MuteUserByUsernameCommand) {
        val sourceUserId = twitterApiClient.me(userFields,tweetFields).data?.id
        val targetUserId = twitterApiClient.getUserByUsername(command.targetUsername, userFields, tweetFields).data?.id
        if (sourceUserId != null && targetUserId != null) {
            val request = MuteUserRequest(targetUserId)
            twitterApiClient.muteUserById(sourceUserId, request)
        }
    }
}
