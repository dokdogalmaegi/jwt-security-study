package study.securityjwt.controller

import mu.KotlinLogging
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import study.securityjwt.config.JwtConfig
import study.securityjwt.dto.UserData
import study.securityjwt.dto.UserRequest
import study.securityjwt.service.UserService

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val userService: UserService,
    private val jwtConfig: JwtConfig
) {

    @PostMapping("/signup")
    fun createUser(@RequestBody userRequest: UserRequest): String {
        userService.createUser(userRequest)

        return "success"
    }

    @PostMapping("/signin")
    fun signIn(@RequestBody userRequest: UserRequest): String {
        val (username, password) = userRequest

        logger.info { "$username 님이 로그인 시도 중" }

        val user = userService.signIn(username, password)
        val roleListString = user.userRoleList.map { it.value }
        return jwtConfig.createToken(user.username, roleListString)
    }

    @GetMapping("/my")
    @PreAuthorize("isAuthenticated()")
    fun getMyInfo(authentication: Authentication): UserData {
        val userData = userService.getUserInfo(authentication)

        logger.info { "${userData.username} 님이 자신의 정보를 조회하였습니다." }
        return userData
    }

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_ADMIN')")
    fun getAllUserInfo(authentication: Authentication): List<UserData> {
        val requestingUser = userService.getUserInfo(authentication)
        logger.info { "${requestingUser.username} 님이 모든 유저의 정보를 조회하였습니다." }

        return userService.getAllUser()
    }

    companion object {
        private val logger = KotlinLogging.logger {}
    }
}
