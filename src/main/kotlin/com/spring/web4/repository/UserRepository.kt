package com.spring.web4.repository

import com.spring.web4.entities.UserEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: CrudRepository<UserEntity, Long>{

    fun existsByLoginAndPassword(login: String, password: String): Boolean

    fun existsByLogin(login: String): Boolean

    fun deleteByLogin(login: String)
}