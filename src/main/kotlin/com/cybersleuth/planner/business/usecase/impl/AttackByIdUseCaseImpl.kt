package com.cybersleuth.planner.business.usecase.impl

import com.cybersleuth.planner.business.bo.AttackBo
import com.cybersleuth.planner.business.usecase.AttackByIdUseCase
import com.cybersleuth.planner.database.repositories.AttackRepository
import com.cybersleuth.planner.ports.AttackPort
import org.springframework.stereotype.Component

@Component
class AttackByIdUseCaseImpl(val attackPort: AttackPort) : AttackByIdUseCase {
    override fun execute(id: Int): AttackBo {
        return attackPort.findById(id)
    }
}