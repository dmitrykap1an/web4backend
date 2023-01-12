package com.spring.web4.utils.security

import jakarta.servlet.http.Cookie
import org.springframework.stereotype.Component

@Component
class JwtProvider(){
    private val jwt = "newton"

    fun getCookie(): Cookie{
        return Cookie("jwt", jwt)
    }
}