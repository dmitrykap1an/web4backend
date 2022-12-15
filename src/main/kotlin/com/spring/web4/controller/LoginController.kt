package com.spring.web4.controller

import com.spring.web4.model.User
import com.spring.web4.service.LoginService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/login")
data class LoginController(
    @Autowired
    val loginService: LoginService
) {

    @GetMapping
    fun getLogin(
        @RequestBody user: User
    ) : ResponseEntity<String>{
         try{
            return ResponseEntity.ok("Сервер работает")
        } catch (e: Exception){
            return ResponseEntity.badRequest().body("Произошла ошибка")
        }

    }
}