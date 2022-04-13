package com.alpsakaci.automuter.application.command

import com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.TwitterApiClient
import com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.request.MuteUserRequest
import com.trendyol.kediatr.CommandHandler
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component

@Component
@Lazy
class MuteUserCommandHandler(
    val twitterApiClient: TwitterApiClient
) : CommandHandler<MuteUserCommand> {

    val userFields = "description,profile_image_url"
    val tweetFields = "entities"

    override fun handle(command: MuteUserCommand) {
        val sourceUserId = twitterApiClient.me(userFields,tweetFields).data?.id
        val request = MuteUserRequest(command.targetUserId)
        if (sourceUserId != null) {
            twitterApiClient.muteUserById(sourceUserId, request)
        }
    }
}
