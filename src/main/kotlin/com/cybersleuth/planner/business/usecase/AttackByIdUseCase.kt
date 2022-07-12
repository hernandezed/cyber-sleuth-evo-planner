package com.cybersleuth.planner.business.usecase

import com.cybersleuth.planner.business.bo.AttackBo


interface AttackByIdUseCase {
    fun execute(id: Int): AttackBo
}