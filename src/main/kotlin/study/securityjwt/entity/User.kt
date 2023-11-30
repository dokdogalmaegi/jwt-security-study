package study.securityjwt.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import lombok.AllArgsConstructor
import lombok.Getter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import study.securityjwt.enum.UserRole

@AllArgsConstructor
@Getter
@Entity
@Table(name = "app_user")
class User(
    @Column(unique = true)
    private val username: String,

    private val password: String
) : UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long = 0L

    @Enumerated(value = EnumType.STRING)
    var userRoleList: MutableList<UserRole> = mutableListOf(UserRole.USER)
        private set

    fun addRole(userRole: UserRole) {
        require(!userRoleList.contains(userRole)) { "이미 존재하는 Role은 추가할 수 없습니다." }

        userRoleList.add(userRole)
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = userRoleList.map { SimpleGrantedAuthority(it.value) }.toMutableList()

    override fun getPassword(): String = password

    override fun getUsername(): String = username

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}
