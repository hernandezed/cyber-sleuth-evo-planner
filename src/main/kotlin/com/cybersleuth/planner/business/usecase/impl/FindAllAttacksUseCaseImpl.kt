package com.cybersleuth.planner.business.usecase.impl

import com.cybersleuth.planner.business.bo.AttackBo
import com.cybersleuth.planner.business.usecase.FindAllAttacksUseCase
import com.cybersleuth.planner.database.repositories.AttackRepository
import org.springframework.stereotype.Component

@Component
class FindAllAttacksUseCaseImpl(val attackRepository: AttackRepository) : FindAllAttacksUseCase {
    override fun execute(): Set<AttackBo> {
        return attackRepository.findAll()
                .map { AttackBo(it.id, it.name, it.attribute, it.type, it.cost, it.power, it.inheritable) }.toSet()
    }
}