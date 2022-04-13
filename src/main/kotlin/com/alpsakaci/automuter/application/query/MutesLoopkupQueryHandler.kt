package com.alpsakaci.automuter.application.query

import com.alpsakaci.automuter.application.model.TwitterMetaData
import com.alpsakaci.automuter.application.model.TwitterUser
import com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.TwitterApiClient
import com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.resonse.BaseResponse
import com.trendyol.kediatr.QueryHandler
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component

@Component
@Lazy
class MutesLoopkupQueryHandler(
    val twitterApiClient: TwitterApiClient
) : QueryHandler<MutesLookupQuery, BaseResponse<ArrayList<TwitterUser>, TwitterMetaData?>> {

    val userFields = "description,profile_image_url"
    val tweetFields = "attachments,lang,entities,referenced_tweets,text"

    override fun handle(query: MutesLookupQuery): BaseResponse<ArrayList<TwitterUser>, TwitterMetaData?> {
        val userId = twitterApiClient.me(userFields, tweetFields).data!!.id
        query.paginationToken?.let {
            return twitterApiClient.mutesLookupPaginated(userId, userFields, tweetFields, 100, query.paginationToken)
        }
        return twitterApiClient.mutesLookup(userId, userFields, tweetFields, 100)
    }
}
