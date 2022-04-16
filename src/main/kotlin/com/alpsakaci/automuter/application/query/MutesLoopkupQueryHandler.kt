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
class MutesLoopkupQueryHandler(
    val twitterApiClient: TwitterApiClient
) : QueryHandler<MutesLookupQuery, BaseModel<Collection<TwitterUser>, TwitterMetaData>> {

    val userFields = "description,profile_image_url"
    val tweetFields = "attachments,lang,entities,referenced_tweets,text"

    override fun handle(query: MutesLookupQuery): BaseModel<Collection<TwitterUser>, TwitterMetaData> {
        var mutedAccounts = mutableListOf<TwitterUser>()
        val userId = twitterApiClient.me(userFields, tweetFields).data!!.id
        var chunk = twitterApiClient.mutesLookup(userId, userFields, tweetFields, query.pageSize)
        chunk.data?.let { mutedAccounts.addAll(it) }

        while (chunk.meta?.next_token != null) {
            val nextToken = chunk.meta!!.next_token
            chunk = twitterApiClient.mutesLookupPaginated(userId, userFields, tweetFields, query.pageSize, nextToken!!)
            chunk.data?.let { mutedAccounts.addAll(it) }
        }

        return BaseModel(mutedAccounts, TwitterMetaData(result_count = mutedAccounts.size, next_token = null, previous_token = null))
    }
}
