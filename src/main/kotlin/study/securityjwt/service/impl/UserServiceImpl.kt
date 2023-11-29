package study.securityjwt.service.impl

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import study.securityjwt.dto.UserRequest
import study.securityjwt.entity.User
import study.securityjwt.repository.UserRepository
import study.securityjwt.service.UserService

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
) : UserService {
    override fun createUser(userRequest: UserRequest) {
        val (username, password) = userRequest

        userRepository.save(
            User(
                username = username,
                password = bCryptPasswordEncoder.encode(password)
            )
        )
    }
}
