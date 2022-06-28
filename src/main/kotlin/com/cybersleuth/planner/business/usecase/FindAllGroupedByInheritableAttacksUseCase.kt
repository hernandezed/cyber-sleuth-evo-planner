package com.cybersleuth.planner.business.usecase

import com.cybersleuth.planner.database.Digimon

interface FindAllGroupedByInheritableAttacksUseCase {
    fun execute(): Map<Int, List<Digimon>>
}