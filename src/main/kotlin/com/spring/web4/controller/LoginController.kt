package com.spring.web4.controller

import com.spring.web4.model.Answer
import com.spring.web4.security.Hashing
import com.spring.web4.service.LoginAndRegistrationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/login")
data class LoginController(
    @Autowired
    private val loginAndRegistrationService: LoginAndRegistrationService
) {

    @GetMapping
    @ResponseBody
    fun getLogin(
        @RequestParam(name = "login") login: String, @RequestParam(name = "password") password: String
    ): ResponseEntity<Answer> {
        try {
            return if (loginAndRegistrationService.validate(login, password)) {
                val pass = getHashedPassword(login, password)
                if (loginAndRegistrationService.isRegistered(login, pass)) {
                    ResponseEntity.ok(Answer("Пользователь зарегистрирован"))
                } else {
                    ResponseEntity.badRequest().body(Answer("Пользователь не найден"))
                }

            } else {
                ResponseEntity.badRequest()
                    .body(Answer("Логин и/или пароль имеют меньше 5 символов"))
            }
        } catch (e: EmptyResultDataAccessException) {
            return ResponseEntity.badRequest().body(Answer("Пользователь не найден"))
        } catch (e: Exception) {
            e.printStackTrace()
            return ResponseEntity.badRequest().body(Answer("Ошибка работы сервера"))
        }
    }

    fun getHashedPassword(login: String, password: String): String {
        val userDetails = loginAndRegistrationService.getSalts(login)
        return Hashing.getHash(password, userDetails.getFirstSalt()!!, userDetails.getSecondSalt()!!)
    }
}