package com.gusto.dsvendas.configurations

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig @Autowired constructor(private val env: Environment): WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        val isTestProfileActive = listOf(env.activeProfiles.contains("test")).first()
        if (isTestProfileActive) http.headers().frameOptions().disable()

        http.cors().and().csrf().disable()
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.authorizeRequests().anyRequest().permitAll()
    }

    fun corsConfigurationSource(): CorsConfigurationSource {
        val cors = CorsConfiguration().applyPermitDefaultValues()
        cors.allowedMethods = listOf("POST", "GET", "PUT", "DELETE", "OPTIONS")
        val url = UrlBasedCorsConfigurationSource()
        url.registerCorsConfiguration("/**", cors)
        return url
    }
}