package study.securityjwt.enum

import lombok.AllArgsConstructor
import lombok.Getter

@AllArgsConstructor
@Getter
enum class UserRole(private val value: String) {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN")
}