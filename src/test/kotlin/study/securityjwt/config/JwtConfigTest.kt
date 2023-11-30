package study.securityjwt.config

import io.mockk.every
import io.mockk.mockk
import jakarta.servlet.http.HttpServletRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class JwtConfigTest {

    @Autowired
    lateinit var jwtConfig: JwtConfig

    private val request: HttpServletRequest = mockk()

    @Test
    fun `Authorization header 값이 null 일 경우, null 이 반환된다`() {
        every { request.getHeader("Authorization") } returns null

        val token = jwtConfig.resolveToken(request)

        assertThat(token).isNull()
    }

    @Test
    fun `Authorization header 값이 Bearer 로 시작하지 않을 경우, null 이 반환된다`() {
        every { request.getHeader("Authorization") } returns "start "

        val token = jwtConfig.resolveToken(request)

        assertThat(token).isNull()
    }

    @Test
    fun `Authorization header 값에 token 값을 반환한다`() {
        every { request.getHeader("Authorization") } returns "Bearer token"

        val token = jwtConfig.resolveToken(request)

        assertThat(token).isEqualTo("token")
    }
}
