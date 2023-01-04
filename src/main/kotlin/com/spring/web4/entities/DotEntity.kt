package com.spring.web4.entities

import jakarta.persistence.*
import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity
@Table(name = "dots")
class DotEntity(): Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long? = null;

    @Column(name = "x")
    private var x: Double? = null;
    @Column(name = "y")
    private var y: Double? = null
    @Column(name = "r")
    private var r: Double? = null
    @Column(name = "hit_result")
    private var hitResult: Boolean? = null
    @Column(name = "server_time")
    private var serverTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
    @Column(name = "execute_time")
    private var executeTime: Long = System.nanoTime()


    fun getX() =
        x;

    fun getY() =
        y;

    fun getR() =
        r;

    fun setX(x: Double?){
        this.x = x;
    }

    fun setY(y: Double?){
        this.y = y;
    }

    fun setR(r: Double?){
        this.r = r;
    }

    fun getServerTime(): String =
        serverTime

    fun setServerTime(serverTime: String){
        this.serverTime = serverTime
    }

    fun getExecuteTime(): Long{
        return executeTime
    }

    fun setExecuteTime(executeTime: Long){
        this.executeTime = executeTime
    }


    fun getId() =
        id;

    fun setId(id: Long?) {
        this.id = id
    }

    fun getHitResult() =
        hitResult

    fun setHitResult(hitResult: Boolean?) {
        this.hitResult = hitResult
    }

    override fun toString(): String {
        return "DotEntity(id: $id, x: $x, y: $y, r: $r, hitResult :$hitResult"
    }



}