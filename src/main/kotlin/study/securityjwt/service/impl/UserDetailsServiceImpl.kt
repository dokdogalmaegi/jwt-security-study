package study.securityjwt.service.impl

import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import study.securityjwt.repository.UserRepository
import java.util.Optional

@Service
class UserDetailsServiceImpl(private val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return Optional.ofNullable(userRepository.findByUsername(username)).orElseThrow {
            throw BadCredentialsException("해당 유저가 존재하지 않습니다.")
        }
    }
}
