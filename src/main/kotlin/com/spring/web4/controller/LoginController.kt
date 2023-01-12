package com.spring.web4.controller

import com.spring.web4.utils.exceptions.NotValidLoginOrPasswordException
import com.spring.web4.utils.model.Answer
import com.spring.web4.service.LoginAndRegistrationService
import com.spring.web4.utils.exceptions.EmptyBodyException
import com.spring.web4.utils.model.User
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/login")
data class LoginController(
    @Autowired
    private val loginAndRegistrationService: LoginAndRegistrationService
) {

    @PostMapping
    @ResponseBody
    fun getLogin(
        @RequestBody(required = false) user: User?, response: HttpServletResponse
    ): ResponseEntity<Answer> {
        return try {
            if (user == null) throw EmptyBodyException()
            if (loginAndRegistrationService.isRegistered(user.getLogin(), user.getPassword())) {
                response.addCookie(loginAndRegistrationService.getCookie(user.getLogin()))
                ResponseEntity.status(HttpStatus.OK).body(Answer("Пользователь зарегистрирован"))
            } else {
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(Answer("Пользователь не найден"))
            }
        } catch (e: EmptyResultDataAccessException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Answer("Пользователь не найден"))
        } catch (e: NotValidLoginOrPasswordException) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(Answer("Логин и/или пароль имеют меньше 5 символов"))
        } catch (e: NullPointerException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Answer("Пользователь не найден"))
        } catch (e: EmptyBodyException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Answer("Данные не получены сервером"))
        }
    }

    @GetMapping
    fun getTest(){

    }
}