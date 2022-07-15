package com.cybersleuth.planner.config

import com.cybersleuth.planner.business.bo.*
import com.cybersleuth.planner.database.repositories.AttackRepository
import com.cybersleuth.planner.ports.DigimonPort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.transaction.Transactional

@Configuration
class DataConfig {

    @Transactional
    @Bean
    fun digimons(digimonPort: DigimonPort): Map<Int, DigimonBo> {
        return digimonPort.findAll().associateBy { it.id }
    }

    @Transactional
    @Bean
    fun attacks(attackRepository: AttackRepository): Map<Int, AttackBo> {
        return attackRepository.findAll().map { AttackBo(it.id, it.name, it.attribute, it.type, it.cost, it.power, it.inheritable) }
                .associateBy { it.id }
    }

    @Bean
    fun digipedia(digimons: Map<Int, DigimonBo>, attacks: Map<Int, AttackBo>): Digipedia {
        return Digipedia(digimons, attacks)
    }


}