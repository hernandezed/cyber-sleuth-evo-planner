package com.cybersleuth.planner.business.usecase

import com.cybersleuth.planner.database.Digimon

interface FindAllDigimonsUseCase {
    fun execute(): Set<Digimon>
}