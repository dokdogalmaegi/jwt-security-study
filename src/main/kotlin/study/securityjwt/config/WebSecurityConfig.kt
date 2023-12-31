package study.securityjwt.config

import lombok.RequiredArgsConstructor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
class WebSecurityConfig(private val jwtConfig: JwtConfig) {

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer {
            it
                .ignoring()
                .requestMatchers("/h2-console/**")
        }
    }

    @Bean
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        httpSecurity.csrf { it.disable() }
            .sessionManagement {
                it
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }.addFilterAt(
                JwtAuthenticationFilter(jwtConfig),
                UsernamePasswordAuthenticationFilter::class.java
            )

        return httpSecurity.build()
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()
}
