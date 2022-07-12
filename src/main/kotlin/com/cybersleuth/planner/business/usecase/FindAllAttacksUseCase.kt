package com.cybersleuth.planner.business.usecase

import com.cybersleuth.planner.business.bo.AttackBo

interface FindAllAttacksUseCase {
    fun execute(): Set<AttackBo>
}