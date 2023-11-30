package study.securityjwt.service.impl

import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import study.securityjwt.dto.UserData
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

    override fun signIn(username: String, password: String): User {
        val findUser = userRepository.findByUsername(username)

        require(findUser != null && bCryptPasswordEncoder.matches(password, findUser.password)) { throw BadCredentialsException("아이디나 비밀번호를 확인해주세요.") }

        return findUser
    }

    override fun getUserInfo(authentication: Authentication): UserData {
        val user: User = authentication.principal as User
        val findUser = userRepository.findByUsername(user.username)!!

        return UserData(findUser.id, findUser.username, findUser.userRoleList)
    }
}
