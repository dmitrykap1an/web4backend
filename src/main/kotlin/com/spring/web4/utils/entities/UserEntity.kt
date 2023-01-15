package com.spring.web4.utils.entities

import com.spring.web4.utils.model.User
import jakarta.persistence.*
import jakarta.servlet.http.Cookie
import java.io.Serializable

@Entity
@Table(
    name = "users",
    uniqueConstraints =
    [UniqueConstraint(columnNames = arrayOf("login"))]
)
class UserEntity : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private var id: Long? = null
    @Column(name = "login")
    private var login: String? = null
    private var password: String? = null
    private var cookie: String? = null
    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "user_details_id")
    private var userDetails: UserDetails? = null

    fun getCookie() =
        cookie

    fun setCookie(cookie: String?){
        this.cookie = cookie
    }
    fun getId() =
        id;

    fun setId(id: Long?) {
        this.id = id;
    }

    public fun getLogin() =
        login

    fun setLogin(login: String?) {
        this.login = login
    }

    fun getPassword() =
        password;

    fun setPassword(password: String?) {
        this.password = password
    }

    fun getUserDetails() =
        userDetails

    fun setUserDetails(userDetails: UserDetails?){
        this.userDetails = userDetails
    }

}