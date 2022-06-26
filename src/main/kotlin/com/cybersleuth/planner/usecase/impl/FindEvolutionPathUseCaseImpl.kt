package com.cybersleuth.planner.usecase.impl

import com.cybersleuth.planner.domain.Attack
import com.cybersleuth.planner.domain.Digimon
import com.cybersleuth.planner.domain.path.PathStep
import com.cybersleuth.planner.finder.EvolutionPathFinder
import com.cybersleuth.planner.usecase.FindEvolutionPathUseCase
import org.springframework.stereotype.Component

@Component
class FindEvolutionPathUseCaseImpl(val pathFinder: EvolutionPathFinder, val digimons: Map<Int, Digimon>, val digimonsByName: Map<String, Digimon>, val attacksById: Map<Int, Attack>, val attacksByName: Map<String, Attack>) : FindEvolutionPathUseCase {

    override fun execute(sourceName: String, targetName: String?, wantedAttacksNames: Set<String>?): List<PathStep> {
        /* val sourceDigimon: Digimon = digimonsByName[sourceName] ?: throw IllegalArgumentException()
         var targetDigimon: Digimon? = null
         if (targetName != null) {
             targetDigimon = digimonsByName[targetName]
             if (targetDigimon == null) {
                 throw IllegalArgumentException()
             }
         }

         var wantedAttacks: Set<Attack?> = setOf()

         if (wantedAttacksNames != null) {
             wantedAttacks = wantedAttacksNames.map { attacksByName[it] }.toSet()
         }

         if (wantedAttacks.any { it == null }) {
             throw IllegalArgumentException()
         }

         if (wantedAttacksNames == null && targetName == null) {
             throw IllegalArgumentException()
         }

         val shortestPath = pathFinder.findShortestPath(sourceDigimon.id, targetDigimon?.id, wantedAttacks.map { it!!.id }.toSet())

         shortestPath.map { digimons[it]!! }*/
        return listOf()
    }

}