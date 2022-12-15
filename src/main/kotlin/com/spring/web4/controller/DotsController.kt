package com.spring.web4.controller

import com.spring.web4.model.Dot
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("dots")
class DotsController {

    @PostMapping
    fun getDot(@RequestBody dot: Dot){

    }
}