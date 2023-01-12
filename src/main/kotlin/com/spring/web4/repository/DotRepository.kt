package com.spring.web4.repository

import com.spring.web4.utils.entities.DotEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface DotRepository: CrudRepository<DotEntity, Long>{
    fun findAllByLogin(login: String?): List<DotEntity>?
}