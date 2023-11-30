package study.securityjwt.dto

import study.securityjwt.enum.UserRole

data class UserData(
    val id: Long,
    val username: String,
    val userRoleList: List<UserRole>
)
