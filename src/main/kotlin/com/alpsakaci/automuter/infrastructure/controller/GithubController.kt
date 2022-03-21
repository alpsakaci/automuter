package com.alpsakaci.automuter.infrastructure.controller

import com.alpsakaci.automuter.infrastructure.controller.request.GithubGetUserRequest
import com.alpsakaci.automuter.infrastructure.controller.response.GithubUserResponse
import com.alpsakaci.automuter.infrastructure.httpclient.githubapi.GitHubApiClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/github")
class GithubController(val gitHubApiClient: GitHubApiClient) {

    @PostMapping("/fetchUserInfo")
    fun fetchGithubUserInfo(@RequestBody githubGetUserRequest: GithubGetUserRequest): GithubUserResponse {
        val res = gitHubApiClient.getUser(githubGetUserRequest.username)
        return GithubUserResponse(res.avatar_url, res.html_url, res.name, res.location, res.bio)
    }

}
