package com.alpsakaci.automuter.infrastructure.configuration.feign

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients(
    value = ["com.alpsakaci.automuter.infrastructure.httpclient"]
)
class FeignClientConfiguration {

}

