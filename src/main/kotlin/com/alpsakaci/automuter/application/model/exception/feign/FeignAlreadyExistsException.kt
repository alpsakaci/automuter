package com.alpsakaci.automuter.application.model.exception.feign

class FeignAlreadyExistsException: FeignException {

    constructor(message: String): super(message)

    constructor(messageCode: String, vararg messageArgs: Any): super(messageCode, messageArgs)
}
