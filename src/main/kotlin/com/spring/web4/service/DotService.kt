package com.spring.web4.service

import com.spring.web4.repository.DotRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
data class DotService(
    @Autowired
    val dotRepository: DotRepository
) {
}