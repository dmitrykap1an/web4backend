package com.spring.web4.controller

import com.spring.web4.utils.entities.DotEntity
import com.spring.web4.utils.model.AllDotsAnswer
import com.spring.web4.service.DotService
import com.spring.web4.utils.exceptions.CookieNotFoundException
import com.spring.web4.utils.exceptions.EmptyBodyException
import com.spring.web4.utils.exceptions.SaveDotException
import com.spring.web4.utils.model.Dot
import jakarta.servlet.http.HttpServletRequest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.WebUtils

@RestController
@RequestMapping("/dots")
data class DotsController(
    @Autowired
    private val dotService: DotService,
) {
    @PostMapping
    @ResponseBody
    fun getDot(
        @RequestBody dot: Dot, request: HttpServletRequest
    ): ResponseEntity<DotEntity> {
        return try {
            val cookie = WebUtils.getCookie(request, "USERSESSION") ?: throw CookieNotFoundException()
            val dotAnswer = dotService.saveDot(cookie, dot.getX(), dot.getY(), dot.getR())
            ResponseEntity.status(HttpStatus.OK).body(dotAnswer)
        } catch (e: EmptyBodyException) {
            val dotEntity = DotEntity()
            dotEntity.setLogin("Пустое тело запроса")
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dotEntity)
        } catch (e: SaveDotException){
            val dotEntity = DotEntity()
            dotEntity.setLogin("Точка не сохранилась")
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dotEntity)
        }
        catch (e: CookieNotFoundException){
            val dotEntity = DotEntity()
            dotEntity.setLogin("Куки не найден")
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dotEntity)
        }
        catch (e: Exception){
            val dotEntity = DotEntity()
            dotEntity.setLogin(e.message)
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dotEntity)
        }
    }

    @GetMapping("/restore")
    fun getAllDots(request: HttpServletRequest): ResponseEntity<String> {
        return try {
            val cookie = WebUtils.getCookie(request, "USERSESSION") ?: throw CookieNotFoundException()
            val dots = dotService.getAllDots(cookie)
            val answerObject = AllDotsAnswer("Точки успешно получены", dots.reversed())
            val answer = Json.encodeToString(answerObject)
            ResponseEntity.status(HttpStatus.OK).body(answer)
        } catch (e: CookieNotFoundException) {
            ResponseEntity.badRequest().body("")
        }catch (e: Exception){
            val answerObject = AllDotsAnswer(e.message!!, listOf())
            val answer = Json.encodeToString(answerObject)
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(answer)
        }
    }
}