package com.alpsakaci.automuter.infrastructure.httpclient.githubapi

import com.alpsakaci.automuter.infrastructure.httpclient.githubapi.response.GithubGetUserResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(
    value = "\${feign.client.config.github-api.name}",
    url = "\${feign.client.config.github-api.url}",
//        configuration = [FeignInterceptorConfiguration::class]
)
interface GitHubApiClient {

    @GetMapping("/users/{username}")
    fun getUser(@PathVariable username: String): GithubGetUserResponse

}
