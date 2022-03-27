package com.alpsakaci.automuter.infrastructure.configuration.oauth

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod

@Configuration
class OAuth2Configuration(
        @Value("\${spring.security.oauth2.client.registration.twitter.client-id}")
        val clientId: String,
        @Value("\${spring.security.oauth2.client.registration.twitter.client-secret}")
        val clientSecret: String,
) : WebSecurityConfigurerAdapter() {

    private val publicMatchers = arrayOf(
            "/",
            "/css/**",
            "/js/**",
            "/img/**")

    override fun configure(http: HttpSecurity) {
        http
                .cors().and().csrf().disable()
                .authorizeRequests().antMatchers(*publicMatchers).permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .authorizationEndpoint()
                .authorizationRequestResolver(CustomAuthorizationRequestResolver(clientRegistrationRepository(), DEFAULT_AUTHORIZATION_REQUEST_BASE_URI))
    }

    @Bean
    fun clientRegistrationRepository(): ClientRegistrationRepository {
        return InMemoryClientRegistrationRepository(spotifyClientRegistration())
    }

    private fun spotifyClientRegistration(): ClientRegistration? {
        return ClientRegistration
                .withRegistrationId("twitter")
                .clientId(clientId)
                .clientSecret(clientSecret)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("http://127.0.0.1:8080/login/oauth2/code/twitter")
                .scope("users.read", "tweet.read", "tweet.write", "like.read")
                .authorizationUri("https://twitter.com/i/oauth2/authorize")
                .tokenUri("https://api.twitter.com/2/oauth2/token")
                .userInfoUri("https://api.twitter.com/2/users/me")
                .userNameAttributeName("data")
                .clientName("twitter")
                .build()
    }

}
