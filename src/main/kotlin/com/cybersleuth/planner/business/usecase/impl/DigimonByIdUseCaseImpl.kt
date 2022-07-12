package com.cybersleuth.planner.business.usecase.impl

import com.cybersleuth.planner.business.bo.DigimonBo
import com.cybersleuth.planner.business.usecase.DigimonByIdUseCase
import com.cybersleuth.planner.ports.DigimonPort
import org.springframework.stereotype.Component

@Component
class DigimonByIdUseCaseImpl(val digimonPort: DigimonPort) : DigimonByIdUseCase {
    override fun execute(id: Int): DigimonBo {
        return digimonPort.findById(id)
    }
}