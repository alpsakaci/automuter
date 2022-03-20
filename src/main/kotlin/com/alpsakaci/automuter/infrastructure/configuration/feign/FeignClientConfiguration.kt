package com.alpsakaci.automuter.infrastructure.configuration.feign

import com.alpsakaci.automuter.infrastructure.configuration.feign.decoder.FeignClientErrorDecoder
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients(value = ["com.alpsakaci.automuter.infrastructure.httpclient"])
class FeignClientConfiguration {

    @Bean
    fun feignClientErrorDecoder(): FeignClientErrorDecoder {
        return FeignClientErrorDecoder()
    }
}
