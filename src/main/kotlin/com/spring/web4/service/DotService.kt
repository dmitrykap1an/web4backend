package com.spring.web4.service

import com.spring.web4.utils.entities.DotEntity
import com.spring.web4.repository.DotRepository
import com.spring.web4.utils.exceptions.SaveDotException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
data class DotService(
    @Autowired
    private val dotRepository: DotRepository,
    @Autowired
    private val loginAndRegistrationService: LoginAndRegistrationService
) {
    fun saveDot(login: String, x: Double?, y: Double?, r: Double?): DotEntity {
        if (validate(x, y, r)) throw SaveDotException()
            val dotEntity = DotEntity()
            with(dotEntity) {
                setLogin(login)
                setX(x)
                setY(y)
                setR(r)
            }
            dotEntity.setHitResult(checkSuccess(dotEntity.getX(), dotEntity.getY(), dotEntity.getR()))
            dotEntity.setExecuteTime((System.nanoTime() - dotEntity.getExecuteTime()) / 1000000)
            dotRepository.save(dotEntity)
            return dotEntity
    }

    fun getAllDots(login: String): List<DotEntity>{
        return dotRepository.findAllByLogin(login)!!
    }

    private fun checkSuccess(x: Double?, y: Double?, r: Double?): Boolean {
        return (x!! <= 0 && x >= -r!! && y!! >= 0 && y <= r / 2) || //проверка на попадание в квадрат
                (x >= 0 && y!! <= 0 && x * x + y * y <= (r!! / 2) * (r / 2)) || //проверка на попадание в четверть окружности
                (y!! >= -x - r!! && y <= 0 && x <= 0) //проверка на попадание в треугольник
    }

    private fun validate(x: Double?, y: Double?, r: Double?): Boolean {
        return (r!! in 1.0..3.0) && (x!! in -3.0..5.0) && (y!! in -5.0..5.0)
    }
}