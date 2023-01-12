package com.spring.web4.utils.model

import com.spring.web4.utils.entities.DotEntity
import kotlinx.serialization.Serializable

@Serializable
data class AllDotsAnswer(private val text: String, private val listOfDots: List<DotEntity>)