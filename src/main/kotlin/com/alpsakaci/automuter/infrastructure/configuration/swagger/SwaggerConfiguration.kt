package com.alpsakaci.automuter.infrastructure.configuration.swagger

import org.springdoc.core.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class SwaggerConfiguration {

    @Bean
    fun publicApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("automuter")
            .packagesToScan("com.alpsakaci.automuter.infrastructure.controller")
            .build()
    }

}
