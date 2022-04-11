package com.alpsakaci.automuter.application.query

import com.alpsakaci.automuter.application.model.TwitterUser
import com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.TwitterApiClient
import com.trendyol.kediatr.QueryHandler
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component

@Component
@Lazy
class FindUnfollowersQueryHandler(
    val twitterApiClient: TwitterApiClient
) : QueryHandler<FindUnfollowersQuery, Collection<TwitterUser>> {

    val userFields = "description,profile_image_url"
    val tweetFields = "attachments,lang,entities,referenced_tweets,text"

    override fun handle(query: FindUnfollowersQuery): Collection<TwitterUser> {
        var following = mutableMapOf<String, TwitterUser>()
        var followers = mutableMapOf<String, TwitterUser>()

        // Get following
        var chunk = twitterApiClient.getFollowing(query.userId, userFields, tweetFields, 1000)
        chunk.data?.forEach {
            following[it.id] = it
        }

        while (chunk.meta?.next_token != null) {
            val nextToken = chunk.meta!!.next_token
            chunk = twitterApiClient.getFollowingPaginated(query.userId, userFields, tweetFields, 1000, nextToken!!)
            chunk?.data?.forEach {
                following[it.id] = it
            }
        }

        // Get followers
        chunk = twitterApiClient.getFollowers(query.userId, userFields, tweetFields, 1000)
        chunk.data?.forEach {
            followers[it.id] = it
        }

        while (chunk.meta?.next_token != null) {
            val nextToken = chunk.meta!!.next_token
            chunk = twitterApiClient.getFollowersPaginated(query.userId, userFields, tweetFields, 1000, nextToken!!)
            chunk?.data?.forEach {
                followers[it.id] = it
            }
        }

        // Difference
        following.keys.removeAll(followers.keys)

        return following.values
    }
}
