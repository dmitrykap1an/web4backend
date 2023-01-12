package com.spring.web4.utils.model

data class JwtResponse(
    private val type: String = "Bearer",
    private var accessToken: String,
    private var refreshToken: String
) {
    fun getType() =
        type
    fun getAccessToken() =
        accessToken

    fun setAccessToken(accessToken: String){
        this.accessToken = accessToken
    }
    fun getRefreshToken() =
        refreshToken

    fun setRefreshToken(refreshToken: String){
        this.refreshToken = refreshToken
    }
}