package study.securityjwt.service

import study.securityjwt.dto.UserRequest
import study.securityjwt.entity.User

interface UserService {
    fun createUser(userRequest: UserRequest)

    fun signIn(username: String, password: String): User
}
