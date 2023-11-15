package org.sopt.dosopttemplate.domain.repository

import org.sopt.dosopttemplate.domain.entity.User

interface AuthRepository {
    suspend fun login(user: User): Result<User?>

    suspend fun signup(user: User): Result<Unit?>
}