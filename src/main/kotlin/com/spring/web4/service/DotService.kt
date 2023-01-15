package com.spring.web4.service

import com.spring.web4.utils.entities.DotEntity
import com.spring.web4.repository.DotRepository
import com.spring.web4.utils.exceptions.SaveDotException
import jakarta.servlet.http.Cookie
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
data class DotService(
    @Autowired
    private val dotRepository: DotRepository,
    @Autowired
    private val loginAndRegistrationService: LoginAndRegistrationService
) {
    fun saveDot(cookie: Cookie, x: Double?, y: Double?, r: Double?): DotEntity {
        if (!validate(x, y, r)) throw SaveDotException()
            val dotEntity = DotEntity()
            val login = loginAndRegistrationService.getLoginByCookie(cookie)
            with(dotEntity) {
                setLogin(login)
                setX(x)
                setY(y)
                setR(r)
            }
            val hitResult = if(checkSuccess(dotEntity.getX(), dotEntity.getY(), dotEntity.getR())) "Точка попала в область"
                else "Точка не попала в область"
            dotEntity.setHitResult(hitResult)
            dotEntity.setExecuteTime((System.nanoTime() - dotEntity.getExecuteTime()) / 1000000)
            dotRepository.save(dotEntity)
            return dotEntity
    }

    fun getAllDots(cookie: Cookie): List<DotEntity>{
        val login = loginAndRegistrationService.getLoginByCookie(cookie)
        return dotRepository.findAllByLogin(login)!!
    }

    private fun checkSuccess(x: Double?, y: Double?, r: Double?): Boolean {
        return (x!! <= 0 && x >= -r!! && y!! >= 0 && y <= r / 2) || //проверка на попадание в квадрат
                (x >= 0 && y!! <= 0 && x * x + y * y <= (r!! / 2) * (r / 2)) || //проверка на попадание в четверть окружности
                (y!! >= -x - r!! && y <= 0 && x <= 0) //проверка на попадание в треугольник
    }

    private fun validate(x: Double?, y: Double?, r: Double?): Boolean {
        return (r!! >= 1.0 && r <= 3.0) && (x!! >= -3.0 && x <= 5.0) && (y!! >= -5.0 && y <= 5.0)
    }
}