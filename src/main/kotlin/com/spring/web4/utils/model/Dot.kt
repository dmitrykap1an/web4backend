package com.spring.web4.utils.model

data class Dot(
    private val x: Double,
    private val y: Double,
    private val r: Double,
){
    fun getX() =
        x;

    fun getY() =
        y

    fun getR() =
        r;
}