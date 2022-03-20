package com.alpsakaci.automuter.infrastructure.configuration.feign

import com.alpsakaci.automuter.infrastructure.configuration.feign.interceptor.FeignClientRequestInterceptor
import feign.Logger
import feign.RequestInterceptor
import org.springframework.context.annotation.Bean

class FeignInterceptorConfiguration {

    @Bean
    fun feignClientRequestInterceptor(): RequestInterceptor {
        return FeignClientRequestInterceptor()
    }

    @Bean
    fun feignLoggerLevel(): Logger.Level {
        return Logger.Level.FULL
    }

}
