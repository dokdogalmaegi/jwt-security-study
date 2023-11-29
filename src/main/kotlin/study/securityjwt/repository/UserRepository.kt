package study.securityjwt.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import study.securityjwt.entity.User

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?

    fun findByUsernameAndPassword(username: String, password: String): User?
}
