package com.spring.web4.repository

import com.spring.web4.utils.entities.UserEntity
import com.spring.web4.utils.model.User
import jakarta.servlet.http.Cookie
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
interface UserRepository: CrudRepository<UserEntity, Long>{

    fun existsByLoginAndPassword(login: String, password: String): Boolean

    fun existsByLogin(login: String): Boolean

    fun findByLogin(login: String): UserEntity

    fun findByCookie(cookie: String): UserEntity

    fun deleteByLogin(login: String)
    @Modifying
    @Query("update UserEntity u set u.cookie = :cookie where u.login = :login")
    fun updateCookieByLogin(
        @Param("login") login: String,
        @Param("cookie") cookie: String
    ): Int
}