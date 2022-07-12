package com.cybersleuth.planner.business.usecase

import com.cybersleuth.planner.business.bo.DigimonBo

interface DigimonByIdUseCase {
    fun execute(id: Int): DigimonBo
}