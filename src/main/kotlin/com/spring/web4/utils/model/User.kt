package com.spring.web4.utils.model

data class User(
    private var login: String,
    private var password: String
){
    fun getLogin() =
        login

    fun setLogin(login: String){
        this.login = login
    }

    fun getPassword() =
        password

    fun setPassword(password: String){
        this.password = password
    }
}