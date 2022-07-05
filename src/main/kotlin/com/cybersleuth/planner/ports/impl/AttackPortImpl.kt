package com.cybersleuth.planner.ports.impl

import com.cybersleuth.planner.business.bo.AttackBo
import com.cybersleuth.planner.database.repositories.AttackRepository
import com.cybersleuth.planner.ports.AttackPort
import org.springframework.stereotype.Component

@Component
class AttackPortImpl(val attackRepository: AttackRepository) : AttackPort {
    override fun findByNames(name: Set<String>): Set<AttackBo> {
        return attackRepository.findAllByNameIn(name)
                .map { AttackBo(it.id, it.name, it.inheritable) }.toSet()
    }

    override fun findByIds(ids: Set<Int>): Set<AttackBo> {
        return attackRepository.findAllById(ids)
                .map { AttackBo(it.id, it.name, it.inheritable) }.toSet()
    }
}