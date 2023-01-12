package com.spring.web4.service

import com.spring.web4.utils.entities.UserDetails
import com.spring.web4.utils.entities.UserEntity
import com.spring.web4.utils.exceptions.NotValidLoginOrPasswordException
import com.spring.web4.repository.UserDetailsRepository
import com.spring.web4.repository.UserRepository
import com.spring.web4.utils.security.Hashing
import com.spring.web4.utils.security.JwtProvider
import jakarta.servlet.http.Cookie
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

@Service
data class LoginAndRegistrationService(
    @Autowired private val userRepository: UserRepository,
    @Autowired private val userDetailsRepository: UserDetailsRepository,
    @Autowired private val jwtProvider: JwtProvider
) {

    private fun validate(login: String, password: String): Boolean {
        return (login.length >= 5 && password.length >= 5)
    }

    fun isRegistered(login: String, password: String): Boolean {
        return if (validate(login, password)) {
            val pass = getHashedPassword(login, password)
            userRepository.existsByLoginAndPassword(login, pass)
        } else {
            throw NotValidLoginOrPasswordException()
        }

    }

    fun isExistUser(login: String): Boolean {
        return userRepository.existsByLogin(login)
    }

    private fun registerUser(user: UserEntity) {
        userRepository.save(user)
    }

    fun saveSalt(userDetails: UserDetails) {
        userDetailsRepository.save(userDetails)
    }

    private fun getHashedPassword(login: String, password: String): String {
        val userDetails = getSalts(login)
        return Hashing.getHash(password, userDetails!!.getFirstSalt()!!, userDetails.getSecondSalt()!!)
    }

    private fun getSalts(login: String): UserDetails? {
        return userDetailsRepository.getUserDetailsByLogin(login)
    }

    fun getCookie(login: String): Cookie{
        val cookie = Cookie("JSESSIONID", login )
        cookie.maxAge = 30 * 24 * 60 * 60
        cookie.path = "/"
        cookie.secure = true
        return cookie
    }

    fun saveUserAndSalt(login: String, password: String): UserEntity {
        val hash = Hashing.doHash(password)
        val userDetails = UserDetails()
        with(userDetails){
            setFirstSalt(hash.second)
            setSecondSalt(hash.third)
            setLogin(login)
        }
        val userEntity = UserEntity()
        with(userEntity) {
            setLogin(login)
            setPassword(hash.first)
            setUserDetails(userDetails)
        }
        registerUser(userEntity)
        return userEntity
    }
}