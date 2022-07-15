package com.cybersleuth.planner.ports.impl

import com.cybersleuth.planner.business.bo.*
import com.cybersleuth.planner.database.Digimon
import com.cybersleuth.planner.database.repositories.DigimonRepository
import com.cybersleuth.planner.ports.DigimonPort
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component

@Component
class DigimonPortImpl(val digimonRepository: DigimonRepository, val digimonMapper: DigimonMapper) : DigimonPort {
    override fun findByName(name: String): DigimonBo {
        val digimon = digimonRepository.findAllByName(name)
        return digimonMapper.map(digimon)
    }

    override fun findByIds(ids: Set<Int>): Set<DigimonBo> {
        return digimonRepository.findAllById(ids).map { digimonMapper.map(it) }.toSet()
    }

    override fun findById(id: Int): DigimonBo {
        return digimonRepository.findById(id).map { digimonMapper.map(it) }.orElseThrow()
    }

    override fun findAll(): Set<DigimonBo> {
        return digimonRepository.findAll(Sort.by(Sort.Order.asc("id"))).map { digimonMapper.map(it) }.toSet()
    }
}

@Component
class DigimonMapper {
    fun map(digimon: Digimon): DigimonBo {
        return DigimonBo(digimon.id, digimon.name, digimon.stage, digimon.type, digimon.attribute, digimon.memory, digimon.slots, digimon.evolveFrom.map { DegenerationBo(it.id, it.name) }.toSet(), digimon.evolveTo.map { EvolutionBo(it.to.id, it.to.name, RequirementBo(it.requirements.level, it.requirements.hp, it.requirements.sp, it.requirements.atk, it.requirements.def, it.requirements.int, it.requirements.spd, it.requirements.cam, it.requirements.abi, it.requirements.dna)) }.toSet(), digimon.learnedAttacks.map { LearnAttackBo(AttackBo(it.learnedAttack.id, it.learnedAttack.name, it.learnedAttack.attribute, it.learnedAttack.type, it.learnedAttack.cost, it.learnedAttack.power, it.learnedAttack.inheritable), it.at) }.toSet(),
                digimon.stats.associateBy { it.level }.mapValues {
                    StatsBo(it.value.id, it.value.hp, it.value.sp,
                            it.value.attack, it.value.defense, it.value.intellect, it.value.speed)
                })
    }
}