package com.cybersleuth.planner.config

import com.cybersleuth.planner.business.bo.AttackBo
import com.cybersleuth.planner.business.bo.DigimonBo
import com.cybersleuth.planner.business.bo.Digipedia
import com.cybersleuth.planner.business.bo.LearnAttackBo
import com.cybersleuth.planner.database.Digimon
import com.cybersleuth.planner.database.repositories.AttackRepository
import com.cybersleuth.planner.database.repositories.DigimonRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.transaction.Transactional

@Configuration
class DataConfig {

    @Transactional
    @Bean
    fun digimons(digimonRepository: DigimonRepository): Map<Int, DigimonBo> {
        return digimonRepository.findAll()
                .map { digimon ->
                    DigimonBo(digimon.id, digimon.evolveFrom.map { it.id }.toSet(), digimon.evolveTo.map { it.to.id }.toSet(),
                            digimon.learnedAttacks.map { LearnAttackBo(it.learnedAttack.id, it.at) }.toSet())
                }

                .associateBy { it.id }
    }

    @Transactional
    @Bean
    fun attacks(attackRepository: AttackRepository): Map<Int, AttackBo> {
        return attackRepository.findAll().map { AttackBo(it.id, it.name, it.inheritable) }
                .associateBy { it.id }
    }

    @Bean
    fun digipedia(digimons: Map<Int, DigimonBo>, attacks: Map<Int, AttackBo>): Digipedia {
        return Digipedia(digimons, attacks)
    }


}