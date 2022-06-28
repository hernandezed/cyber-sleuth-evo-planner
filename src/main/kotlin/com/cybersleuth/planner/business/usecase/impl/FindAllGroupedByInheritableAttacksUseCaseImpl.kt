package com.cybersleuth.planner.business.usecase.impl

import com.cybersleuth.planner.business.usecase.FindAllGroupedByInheritableAttacksUseCase
import com.cybersleuth.planner.database.Digimon
import com.cybersleuth.planner.database.repositories.DigimonRepository
import org.springframework.stereotype.Component
import javax.transaction.Transactional

@Component
class FindAllGroupedByInheritableAttacksUseCaseImpl(val digimonRepository: DigimonRepository) : FindAllGroupedByInheritableAttacksUseCase {
    @Transactional
    override fun execute(): Map<Int, List<Digimon>> {
        val result: MutableMap<Int, MutableList<Digimon>> = HashMap()
        digimonRepository.findAll().forEach { digimon ->
            digimon.learnedAttacks.forEach {
                if (it.learnedAttack.inheritable!!) {
                    if (result[it.learnedAttack.id] == null) {
                        result[it.learnedAttack.id] = ArrayList()
                    }
                    result[it.learnedAttack.id]!!.add(digimon)
                }
            }
        }
        return result
    }
}