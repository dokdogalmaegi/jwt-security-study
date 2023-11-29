package study.securityjwt.service

import study.securityjwt.dto.UserRequest

interface UserService {
    fun createUser(userRequest: UserRequest)
}
