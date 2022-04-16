package com.alpsakaci.automuter.application.command

import com.trendyol.kediatr.Command

class MuteAllFollowersByUsernameCommand(
    val targetUsername: String,
    val tweetAfterProcess: Boolean
): Command
