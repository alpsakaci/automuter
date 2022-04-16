package com.alpsakaci.automuter.application.query

import com.alpsakaci.automuter.application.model.TwitterMetaData
import com.alpsakaci.automuter.application.model.TwitterUser
import com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.TwitterApiClient
import com.alpsakaci.automuter.application.model.BaseModel
import com.trendyol.kediatr.QueryHandler
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component

@Component
@Lazy
class FindAllFollowersQueryHandler(
    val twitterApiClient: TwitterApiClient
) : QueryHandler<FindAllFollowersQuery, BaseModel<Collection<TwitterUser>, TwitterMetaData>> {

    val userFields = "description,profile_image_url"
    val tweetFields = "attachments,lang,entities,referenced_tweets,text"

    override fun handle(query: FindAllFollowersQuery): BaseModel<Collection<TwitterUser>, TwitterMetaData> {
        var followers = mutableListOf<TwitterUser>()
        var chunk = twitterApiClient.getFollowers(query.userId, userFields, tweetFields, 1000)
        chunk.data?.let { followers.addAll(it) }

        while (chunk.meta?.next_token != null) {
            val nextToken = chunk.meta!!.next_token
            chunk = twitterApiClient.getFollowersPaginated(query.userId, userFields, tweetFields, 1000, nextToken!!)
            chunk.data?.let { followers.addAll(it) }
        }

        return BaseModel(followers, TwitterMetaData(result_count = followers.size, next_token = null, previous_token = null))
    }
}
