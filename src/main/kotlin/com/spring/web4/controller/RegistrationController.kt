package com.spring.web4.controller

import com.spring.web4.utils.model.Answer
import com.spring.web4.service.LoginAndRegistrationService
import com.spring.web4.utils.exceptions.EmptyBodyException
import com.spring.web4.utils.model.User
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/reg")
data class RegistrationController(
    @Autowired
    private val loginAndRegistrationService: LoginAndRegistrationService
) {
    @PostMapping
    @ResponseBody
    fun getUser(
        @RequestBody(required = false) user: User?, response: HttpServletResponse
    ): ResponseEntity<Answer> {
        return try {
            if (user == null) throw EmptyBodyException()
            when {
                loginAndRegistrationService.isExistUser(user.getLogin()) -> {
                    response.addCookie(loginAndRegistrationService.getCookie(user.getLogin()))
                    ResponseEntity.status(HttpStatus.FORBIDDEN).body(Answer("Пользователь с таким именем уже существует"))
                }

                else -> {
                    loginAndRegistrationService.saveUserAndSalt(user.getLogin(), user.getPassword())
                    ResponseEntity.status(HttpStatus.OK).body(Answer("Пользователь успешно зарегистрирован"))  // TODO: Добавить отлов ошибок
                }
            }
        }
        catch (e: EmptyBodyException){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Answer("Данные не получены сервером"))
        } catch (e: Exception){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Answer("Ошибка работы сервера"))
        }
    }
}
