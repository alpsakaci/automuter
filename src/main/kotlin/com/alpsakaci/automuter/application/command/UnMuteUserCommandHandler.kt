package com.alpsakaci.automuter.application.command

import com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.TwitterApiClient
import com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.request.MuteUserRequest
import com.trendyol.kediatr.CommandHandler
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component

@Component
@Lazy
class UnMuteUserCommandHandler(
    val twitterApiClient: TwitterApiClient
) : CommandHandler<UnMuteUserCommand> {

    val userFields = "description,profile_image_url"
    val tweetFields = "entities"

    override fun handle(command: UnMuteUserCommand) {
        val sourceUserId = twitterApiClient.me(userFields,tweetFields).data?.id
        if (sourceUserId != null) {
            twitterApiClient.unmuteUserById(sourceUserId, command.targetUserId)
        }
    }
}
