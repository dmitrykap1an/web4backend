package com.spring.web4.utils.entities

import jakarta.persistence.*
import java.io.Serializable

@Entity
class UserDetails(): Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long? = null
    private var firstSalt: String? = null
    private var secondSalt: String? = null
    private var login: String? = null

    fun getId() =
        id

    fun setId(id: Long?){
        this.id = id
    }

    fun getFirstSalt() =
        firstSalt

    fun setFirstSalt(firstSalt: String?){
        this.firstSalt = firstSalt
    }

    fun getSecondSalt() =
        secondSalt

    fun setSecondSalt(secondSalt: String?){
        this.secondSalt = secondSalt
    }

    fun getLogin() =
        login

    fun setLogin(login: String?) {
        this.login = login
    }


}