package com.alpsakaci.automuter.application.query

import com.alpsakaci.automuter.application.model.BaseModel
import com.alpsakaci.automuter.application.model.TwitterMetaData
import com.alpsakaci.automuter.application.model.TwitterUser
import com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.TwitterApiClient
import com.trendyol.kediatr.CommandBus
import com.trendyol.kediatr.QueryHandler
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component

@Component
@Lazy
class FindUnfollowersQueryHandler(
    var commandBus: CommandBus
) : QueryHandler<FindUnfollowersQuery, BaseModel<Collection<TwitterUser>, TwitterMetaData>> {

    val userFields = "description,profile_image_url"
    val tweetFields = "attachments,lang,entities,referenced_tweets,text"

    override fun handle(query: FindUnfollowersQuery): BaseModel<Collection<TwitterUser>, TwitterMetaData> {
        var followingQuery = FindAllFollowingQuery(query.userId)
        var followersQuery = FindAllFollowersQuery(query.userId)
        var following = commandBus.executeQuery(followingQuery).data as ArrayList
        var followers = commandBus.executeQuery(followersQuery).data as ArrayList
        following.removeAll(followers)

        return BaseModel(following, TwitterMetaData(following.size, null, null))
    }
}
