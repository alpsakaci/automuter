package com.alpsakaci.automuter.application.command

import com.alpsakaci.automuter.application.query.FindAllFollowersQuery
import com.alpsakaci.automuter.application.query.FindAllFollowingQuery
import com.alpsakaci.automuter.application.query.MutesLookupQuery
import com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.TwitterApiClient
import com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.request.CreateTweetRequest
import com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.request.MuteUserRequest
import com.trendyol.kediatr.AsyncCommandHandler
import com.trendyol.kediatr.CommandBus
import kotlinx.coroutines.delay
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component

@Component
@Lazy
class MuteAllFollowersByUsernameCommandHandler(
    val twitterApiClient: TwitterApiClient,
    val commandBus: CommandBus
) : AsyncCommandHandler<MuteAllFollowersByUsernameCommand> {

    val userFields = "description,profile_image_url"
    val tweetFields = "entities"

    override suspend fun handleAsync(command: MuteAllFollowersByUsernameCommand) {
        val authUser = twitterApiClient.me(userFields,tweetFields).data?.id
        val targetUserId = twitterApiClient.getUserByUsername(command.targetUsername, userFields, tweetFields).data?.id
        val findAllFollowingQuery = FindAllFollowingQuery(authUser!!)
        val following = commandBus.executeQuery(findAllFollowingQuery).data
        val mutesLookupQuery = MutesLookupQuery(1000)
        val alreadyMuted = commandBus.executeQuery(mutesLookupQuery).data

        println("Already muted size: ${alreadyMuted!!.size}")

        val findAllFollowersQuery = FindAllFollowersQuery(targetUserId!!)
        val targetFollowers = commandBus.executeQuery(findAllFollowersQuery).data

        var counter = 0
        targetFollowers?.forEach { it ->
            val muteUserRequest = MuteUserRequest(it.id)

            if (alreadyMuted != null && alreadyMuted.contains(it)) {
                println("${it.username} is already muted. Skipping")
            } else if(it.id == authUser) {
                println("You can't mute yourself. Skipping")
            } else if (following != null && following.contains(it)) {
                println("You are following ${it.username}. Skipping")
            } else {
                twitterApiClient.muteUserById(authUser, muteUserRequest)
                counter++
                println("${it.username} muted. Counter: $counter")
                delay(19000L) // Rate limit delay
            }
        }

        if (counter != 0 && command.tweetAfterProcess) {
            val createTweetRequest = CreateTweetRequest("I have muted $counter accounts with @automuter app. \uD83E\uDD2A \uD83D\uDD07")
            twitterApiClient.createTweet(createTweetRequest)
        }
        println("MuteAllFollowersCommand -> Counter: $counter, Tweet process: ${command.tweetAfterProcess}")
    }
}
