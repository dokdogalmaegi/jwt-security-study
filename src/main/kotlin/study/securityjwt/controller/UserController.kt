package study.securityjwt.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import study.securityjwt.config.JwtConfig
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
}
