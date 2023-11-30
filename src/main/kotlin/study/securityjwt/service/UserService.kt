package study.securityjwt.service

import org.springframework.security.core.Authentication
import study.securityjwt.dto.UserData
import study.securityjwt.dto.UserRequest
import study.securityjwt.entity.User

interface UserService {
    fun createUser(userRequest: UserRequest)

    fun signIn(username: String, password: String): User

    fun getUserInfo(authentication: Authentication): UserData

    fun getAllUser(): List<UserData>
}
