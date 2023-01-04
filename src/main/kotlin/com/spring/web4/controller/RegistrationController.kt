package com.spring.web4.controller

import com.spring.web4.entities.UserDetails
import com.spring.web4.entities.UserEntity
import com.spring.web4.model.Answer
import com.spring.web4.security.Hashing
import com.spring.web4.service.LoginAndRegistrationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/reg")
data class RegistrationController(
    @Autowired
    private val loginAndRegistrationService: LoginAndRegistrationService
) {
    @GetMapping
    @ResponseBody
    fun getUser(
        //@RequestBody user: User
        @RequestParam(name = "login") login: String, @RequestParam(name = "password") password: String
    ): ResponseEntity<Answer> {
        return when {
            loginAndRegistrationService.isExistUser(login) -> {
                ResponseEntity.badRequest()
                    .body(Answer("Пользователь с таким именем уже существует"))
            }

            else -> {
                val hash = Hashing.doHash(password)
                saveUser(login, hash.first, hash.second, hash.third)
                ResponseEntity.ok(Answer("Пользователь успешно зарегистрирован"))
            }
        }
    }


    private fun saveUser(login: String, password: String, salt1: String, salt2: String): UserEntity{
        val userDetails = UserDetails()
        with(userDetails){
            setFirstSalt(salt1)
            setSecondSalt(salt2)
            setLogin(login)
        }
        val userEntity = UserEntity()
        with(userEntity) {
            setLogin(login)
            setPassword(password)
            setUserDetails(userDetails)
        }
        loginAndRegistrationService.registerUser(userEntity)
        return userEntity
    }
}
