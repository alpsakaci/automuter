package com.alpsakaci.automuter.infrastructure.configuration.feign.decoder

import com.alpsakaci.automuter.application.model.exception.feign.*
import com.alpsakaci.automuter.infrastructure.configuration.feign.response.FeignErrorResponse
import feign.FeignException.errorStatus
import feign.Response
import feign.codec.ErrorDecoder
import org.apache.commons.io.IOUtils
import org.springframework.http.HttpStatus
import java.lang.Exception

class FeignClientErrorDecoder: ErrorDecoder {

    override fun decode(methodKey: String, response: Response): Exception {
        val errorMessage = if (response.body() != null) {
            IOUtils.toString(response.body().asInputStream())
        } else ""

        return when {
            HttpStatus.valueOf(response.status()) == HttpStatus.UNAUTHORIZED ->
                FeignAuthenticationException(FeignErrorResponse(response.status(), errorMessage).toJson(), response)
            HttpStatus.valueOf(response.status()) == HttpStatus.NOT_FOUND ->
                FeignNotFoundException(FeignErrorResponse(response.status(), errorMessage).toJson(), response)
            HttpStatus.valueOf(response.status()) == HttpStatus.REQUEST_TIMEOUT || HttpStatus.valueOf(response.status()) == HttpStatus.GATEWAY_TIMEOUT ->
                FeignTimeOutException(FeignErrorResponse(response.status(), errorMessage).toJson(), response)
            HttpStatus.valueOf(response.status()).is4xxClientError ->
                FeignClientException(FeignErrorResponse(response.status(), errorMessage).toJson(), response)
            HttpStatus.valueOf(response.status()).is5xxServerError ->
                FeignServerException(errorMessage, response)
            else -> errorStatus(methodKey, response)
        }
    }

}
