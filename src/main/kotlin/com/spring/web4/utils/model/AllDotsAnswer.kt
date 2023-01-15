package com.spring.web4.utils.model

import com.spring.web4.utils.entities.DotEntity
import kotlinx.serialization.Serializable

@Serializable
data class AllDotsAnswer(val text: String, val listOfDots: List<DotEntity>)