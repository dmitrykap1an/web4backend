package com.spring.web4.repository

import com.spring.web4.entities.UserDetails
import org.springframework.data.repository.CrudRepository

interface UserDetailsRepository: CrudRepository<UserDetails, Long>{

    fun getUserDetailsByLogin(login: String): UserDetails
}