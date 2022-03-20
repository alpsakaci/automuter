package com.alpsakaci.automuter.application.model.exception

open class BaseException : RuntimeException {

    val messageCode: String
    val messageArgs: Array<out Any>?

    constructor(messageCode: String) : super(messageCode) {
        this.messageCode = messageCode
        this.messageArgs = null
    }

    constructor(messageCode: String, messageArgs: Array<out Any>?) : super(messageCode) {
        val message = String.format(messageCode, *messageArgs ?: emptyArray())
        this.messageCode = message
        this.messageArgs = messageArgs
    }

}
