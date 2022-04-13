package com.alpsakaci.automuter.application.command

import com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.TwitterApiClient
import com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.request.CreateTweetRequest
import com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.request.MuteUserRequest
import com.trendyol.kediatr.CommandHandler
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component

@Component
@Lazy
class MuteAllFollowersByUsernameCommandHandler(
    val twitterApiClient: TwitterApiClient
) : CommandHandler<MuteAllFollowersByUsernameCommand> {

    val userFields = "description,profile_image_url"
    val tweetFields = "entities"

    override fun handle(command: MuteAllFollowersByUsernameCommand) {
        val authUser = twitterApiClient.me(userFields,tweetFields).data?.id
        val userId = twitterApiClient.getUserByUsername(command.targetUsername, userFields, tweetFields).data?.id
        val getFollowersResponse = twitterApiClient.getFollowers(userId!!, userFields, tweetFields,1000)
        val followers = getFollowersResponse.data
        var nextToken = getFollowersResponse.meta?.next_token
        while (nextToken != null) {
            val res = twitterApiClient.getFollowersPaginated(userId!!, userFields, tweetFields,1000, nextToken)
            nextToken = res.meta?.next_token
            res?.data?.let {
                followers?.addAll(it)
            }
        }

        var counter = 0
        followers?.forEach { it ->
            val muteUserRequest = MuteUserRequest(it.id)
            if (it.id != authUser) {
                val muteResponse = twitterApiClient.muteUserById(authUser!!, muteUserRequest)
                muteResponse.data?.muting.let { m ->
                    if (m == true) counter++
                }
            }
        }

        if (counter != 0) {
            val createTweetRequest = CreateTweetRequest("I have muted $counter accounts with @automuter app. \uD83E\uDD2A \uD83D\uDD07")
            twitterApiClient.createTweet(createTweetRequest)
        }
    }
}
