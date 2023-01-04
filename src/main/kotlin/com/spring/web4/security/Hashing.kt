package com.spring.web4.security


import java.lang.StringBuilder
import java.math.BigInteger
import java.security.MessageDigest

object Hashing{

    fun doHash(password: String): Triple<String, String, String>{
        val salt1 = generateSalt()
        val salt2 = generateSalt()
        return Triple(md5(salt1 + md5(password) + salt2), salt1, salt2)
    }

    fun getHash(password: String, salt1: String, salt2: String): String{
        return md5(salt1 + md5(password) + salt2)
    }

    private fun md5(password: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(password.toByteArray())).toString(16).padStart(32, '0')
    }

    private fun generateSalt(): String {
        val salt = StringBuilder()
        for(i in 1..20){
            val randomValue = (65..90).random()
            println(randomValue)
            salt.append(randomValue.toChar().lowercase())
        }
        return salt.toString()
    }
}



