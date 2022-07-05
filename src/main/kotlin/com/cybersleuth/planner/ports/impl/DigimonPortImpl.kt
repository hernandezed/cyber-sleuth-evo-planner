package com.cybersleuth.planner.ports.impl

import com.cybersleuth.planner.business.bo.*
import com.cybersleuth.planner.database.repositories.DigimonRepository
import com.cybersleuth.planner.ports.DigimonPort
import org.springframework.stereotype.Component

@Component
class DigimonPortImpl(val digimonRepository: DigimonRepository) : DigimonPort {
    override fun findByName(name: String): DigimonBo {
        val digimon = digimonRepository.findAllByName(name)
        return DigimonBo(digimon.id, digimon.name, digimon.evolveFrom.map { it.id }.toSet(), digimon.evolveTo.map { EvolutionBo(it.to.id, RequirementBo(it.requirements.level, it.requirements.hp, it.requirements.sp, it.requirements.atk, it.requirements.def, it.requirements.int, it.requirements.spd, it.requirements.cam, it.requirements.abi, it.requirements.dna)) }.toSet(), digimon.learnedAttacks.map { LearnAttackBo(AttackBo(it.learnedAttack.id, it.learnedAttack.name, it.learnedAttack.inheritable), it.at) }.toSet())
    }

    override fun findByIds(ids: Set<Int>): Set<DigimonBo> {
        return digimonRepository.findAllById(ids).map { digimon ->
            DigimonBo(digimon.id, digimon.name, digimon.evolveFrom.map { it.id }.toSet(), digimon.evolveTo.map { EvolutionBo(it.to.id, RequirementBo(it.requirements.level, it.requirements.hp, it.requirements.sp, it.requirements.atk, it.requirements.def, it.requirements.int, it.requirements.spd, it.requirements.cam, it.requirements.abi, it.requirements.dna)) }.toSet(), digimon.learnedAttacks.map { LearnAttackBo(AttackBo(it.learnedAttack.id, it.learnedAttack.name, it.learnedAttack.inheritable), it.at) }.toSet())
        }.toSet()
    }
}