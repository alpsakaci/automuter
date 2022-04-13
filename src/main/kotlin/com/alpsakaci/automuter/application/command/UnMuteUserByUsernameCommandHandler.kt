package com.alpsakaci.automuter.application.command

import com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.TwitterApiClient
import com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.request.MuteUserRequest
import com.trendyol.kediatr.CommandHandler
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component

@Component
@Lazy
class UnMuteUserByUsernameCommandHandler(
    val twitterApiClient: TwitterApiClient
) : CommandHandler<UnMuteUserByUsernameCommand> {

    val userFields = "description,profile_image_url"
    val tweetFields = "entities"

    override fun handle(command: UnMuteUserByUsernameCommand) {
        val sourceUserId = twitterApiClient.me(userFields,tweetFields).data?.id
        val targetUserId = twitterApiClient.getUserByUsername(command.username, userFields, tweetFields).data?.id
        if (sourceUserId != null && targetUserId != null) {
            twitterApiClient.unmuteUserById(sourceUserId, targetUserId)
        }
    }
}
