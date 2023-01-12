package com.spring.web4.controller

import com.spring.web4.utils.entities.DotEntity
import com.spring.web4.utils.exceptions.NotValidLoginOrPasswordException
import com.spring.web4.utils.model.AllDotsAnswer
import com.spring.web4.utils.model.Answer
import com.spring.web4.service.DotService
import com.spring.web4.utils.exceptions.EmptyBodyException
import com.spring.web4.utils.exceptions.SaveDotException
import com.spring.web4.utils.model.Dot
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.coyote.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.CookieStore

@RestController
@RequestMapping("/dots")
data class DotsController(
    @Autowired
    private val dotService: DotService,
) {
    @PostMapping
    @ResponseBody
    fun getDot(
        @RequestBody(required = false) dot: Dot?, request: HttpServletRequest, @CookieValue(value = "JSESSIONID") login: String?
    ): ResponseEntity<DotEntity> {
        return try {
            if (dot == null) throw EmptyBodyException()
            if(login.isNullOrEmpty()) throw SaveDotException()
            val dotAnswer = dotService.saveDot(login, dot.getX(), dot.getY(), dot.getR())
            ResponseEntity.status(HttpStatus.OK).body(dotAnswer)
        } catch (e: EmptyBodyException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(DotEntity())
        } catch (e: SaveDotException){
            val dotEntity = DotEntity()
            val cookie = request.cookies
            dotEntity.setLogin("Куки не получен + $cookie")
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dotEntity)
        }
    }

    @GetMapping("/restore")
    @ResponseBody
    fun getAllDots(
        @RequestParam(name = "login") login: String,
        @RequestParam(name = "password") password: String
    ): ResponseEntity<AllDotsAnswer> {
        return try {
            val dots = dotService.getAllDots(login)
            ResponseEntity.ok(AllDotsAnswer("Точки успешно получены", dots))
        } catch (e: NotValidLoginOrPasswordException) {
            ResponseEntity.badRequest().body(AllDotsAnswer("Логин и/или пароль имеют меньше 5 символов", listOf()))
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseEntity.ok(AllDotsAnswer("Произошла ошибка", listOf<DotEntity>()))
        }
    }
}