package com.spring.web4.controller

import com.spring.web4.model.Dot
import com.spring.web4.service.DotService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.annotation.SessionScope

@RestController
@RequestMapping("dots")
data class DotsController(
    @Autowired
    private val dotService: DotService
) {
    @PostMapping
    fun getDot(@RequestBody dot: Dot): ResponseEntity<String>{
        return ResponseEntity.ok("") // TODO:
    }
}