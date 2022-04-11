package com.alpsakaci.automuter.application.query

import com.alpsakaci.automuter.application.model.TwitterUser
import com.trendyol.kediatr.Query

data class FindUnfollowersQuery(
    val userId: String,
): Query<Collection<TwitterUser>>
