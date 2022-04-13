package com.alpsakaci.automuter.application.query

import com.alpsakaci.automuter.application.model.TwitterMetaData
import com.alpsakaci.automuter.application.model.TwitterUser
import com.alpsakaci.automuter.infrastructure.httpclient.twitterapi.resonse.BaseResponse
import com.trendyol.kediatr.Query

data class MutesLookupQuery(
    val paginationToken: String?,
): Query<BaseResponse<ArrayList<TwitterUser>, TwitterMetaData?>>
