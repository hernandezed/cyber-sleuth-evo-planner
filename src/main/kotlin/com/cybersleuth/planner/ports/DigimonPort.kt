package com.cybersleuth.planner.ports

import com.cybersleuth.planner.business.bo.DigimonBo

interface DigimonPort {
    fun findByName(name: String): DigimonBo
    fun findByIds(ids: Set<Int>): Set<DigimonBo>
    fun findById(id: Int): DigimonBo
    fun findAll(): Set<DigimonBo>
    fun findAllByAttackId(attackId: Int): Set<DigimonBo>
}