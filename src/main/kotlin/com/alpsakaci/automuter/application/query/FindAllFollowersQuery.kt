package com.alpsakaci.automuter.application.query

import com.alpsakaci.automuter.application.model.TwitterMetaData
import com.alpsakaci.automuter.application.model.TwitterUser
import com.alpsakaci.automuter.application.model.BaseModel
import com.trendyol.kediatr.Query

data class FindAllFollowersQuery(
    val userId: String,
): Query<BaseModel<Collection<TwitterUser>, TwitterMetaData>>
