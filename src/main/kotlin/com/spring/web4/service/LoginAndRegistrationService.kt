package com.spring.web4.service

import com.spring.web4.entities.UserDetails
import com.spring.web4.entities.UserEntity
import com.spring.web4.repository.UserDetailsRepository
import com.spring.web4.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
data class LoginAndRegistrationService(
    @Autowired
    private val userRepository: UserRepository,
    @Autowired
    private val userDetailsRepository: UserDetailsRepository
) {

    fun validate(login: String, password: String): Boolean{
        return (login.length >= 5 && password.length >= 5)
    }

    fun isRegistered(login: String, password: String): Boolean{
        return userRepository.existsByLoginAndPassword(login, password)
    }

    fun isExistUser(login: String): Boolean{
        return userRepository.existsByLogin(login)
    }

    fun registerUser(user: UserEntity){
        userRepository.save(user)
    }

    fun saveSalt(userDetails: UserDetails){
        userDetailsRepository.save(userDetails)
    }

    fun getSalts(login: String): UserDetails{
       return userDetailsRepository.getUserDetailsByLogin(login)
    }
}