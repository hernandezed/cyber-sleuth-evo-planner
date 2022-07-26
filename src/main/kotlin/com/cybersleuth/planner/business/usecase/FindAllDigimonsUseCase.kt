package com.cybersleuth.planner.business.usecase

import com.cybersleuth.planner.business.bo.DigimonBo

interface FindAllDigimonsUseCase {
    fun execute(attackId: Int?): Set<DigimonBo>
}