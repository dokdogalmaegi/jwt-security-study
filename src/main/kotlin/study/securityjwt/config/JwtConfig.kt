package study.securityjwt.config

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import jakarta.servlet.http.HttpServletRequest
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.util.Base64
import java.util.Date

@Component
class JwtConfig(
    @Value(value = "\${JWT_TOKEN}")
    private var secretKey: String,

    @Value(value = "\${JWT_EXPIRE_TIME}")
    private val expireTime: Long = 0L,

    private val userDetailsService: UserDetailsService
) {
    @PostConstruct
    protected fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
    }

    fun createToken(username: String, roles: List<String>): String {
        logger.info { "createToken username: $username, roles: $roles" }

        val now = Date()

        return Jwts.builder()
            .subject(username)
            .issuedAt(now)
            .expiration(Date(now.time + expireTime))
            .claim("roles", roles)
            .signWith(Keys.hmacShaKeyFor(secretKey.toByteArray()))
            .compact()
    }

    fun getAuthentication(token: String): Authentication {
        val parseSignedClaims =
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secretKey.toByteArray())).build().parseSignedClaims(token)
        val username = parseSignedClaims.payload.subject

        val userDetails = userDetailsService.loadUserByUsername(username)
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun resolveToken(request: HttpServletRequest): String? {
        val token = request.getHeader("Authorization") ?: return null

        return token.startsWith("Bearer ").let {
            if (!it) null else token.substring(7)
        }
    }

    fun validateToken(token: String): Boolean {
        return try {
            val parseSignedClaims =
                Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secretKey.toByteArray())).build().parseSignedClaims(token)

            return parseSignedClaims.payload.expiration.after(Date())
        } catch (e: Exception) {
            logger.error(e) { "validateToken error" }
            false
        }
    }

    companion object {
        private val logger = KotlinLogging.logger {}
    }
}
