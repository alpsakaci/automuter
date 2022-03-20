package com.alpsakaci.automuter.application.model.exception.feign

class FeignServerException: FeignException {

    constructor(message: String): super(message)

    constructor(messageCode: String, vararg messageArgs: Any): super(messageCode, messageArgs)
}
