package com.cybersleuth.planner.config

import com.cybersleuth.planner.database.Evolution
import com.cybersleuth.planner.database.LearnAttack
import com.cybersleuth.planner.database.Requirements
import com.cybersleuth.planner.database.Skill
import com.cybersleuth.planner.database.repositories.AttackRepository
import com.cybersleuth.planner.database.repositories.DigimonRepository
import com.cybersleuth.planner.database.repositories.SkillRepository
import com.cybersleuth.planner.domain.Attack
import com.cybersleuth.planner.domain.Digimon
import com.cybersleuth.planner.finder.model.DigimonData
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.util.ResourceUtils

@Configuration
class DataConfig(objectMapper: ObjectMapper, attackRepository: AttackRepository, skillRepository: SkillRepository,
                 digimonRepository: DigimonRepository) {

    init {
        val digimonsJsonFile = String(ResourceUtils.getFile("classpath:db.json").readBytes())
        val attacksJsonFile = String(ResourceUtils.getFile("classpath:attacks.json").readBytes())
        val skillsJsonFile = String(ResourceUtils.getFile("classpath:skills.json").readBytes())
        val attacks = objectMapper.readValue<Set<Attack>>(attacksJsonFile)
        val digimons = objectMapper.readValue<Set<Digimon>>(digimonsJsonFile)
        val skills = objectMapper.readValue<Set<Skill>>(skillsJsonFile)
        val skillsByName = skillRepository.saveAll(skills).associateBy { it.name }
        val digimonsByName = digimons.associateBy { it.name }


        val attacksEntities = attackRepository.saveAll(attacks.map { com.cybersleuth.planner.database.Attack(it.id, it.name, null) }).associateBy { it.id }


        val digiEntities = digimonRepository.saveAll(digimons.map {
            com.cybersleuth.planner.database.Digimon(it.id, it.name, it.portrait, it.mini, it.level, it.type,
                    it.memory, it.slots, skillsByName[it.skill.name]!!, mutableSetOf(), mutableSetOf(), mutableSetOf())
        }.toSet()).associateBy { it.name }

        digiEntities.keys.forEach { it ->
            var actualDigimonEntity = digiEntities[it]
            var actualDigimonData = digimonsByName[it]
            actualDigimonEntity!!.evolveTo.addAll(actualDigimonData!!.evolveTo.map {
                Evolution(null, actualDigimonEntity,
                        digiEntities[it.to]!!, Requirements(it.requirements.level, it.requirements.hp, it.requirements.sp,
                        it.requirements.atk, it.requirements.def, it.requirements.int, it.requirements.spd,
                        it.requirements.cam, it.requirements.abi, it.requirements.dna))
            })
            actualDigimonEntity!!.evolveFrom.addAll(actualDigimonData.evolveFrom.map { digiEntities[it]!! })
            actualDigimonEntity!!.learnedAttacks.addAll(actualDigimonData.attacks.map {
                attacksEntities[it.id]!!.inheritable = it.inheritable
                LearnAttack(null, actualDigimonEntity, attacksEntities[it.id]!!, it.level)
            })
        }
        digimonRepository.saveAll(digiEntities.values)
    }

    @Bean
    fun digimonsById(objectMapper: ObjectMapper): Map<Int, Digimon> {
        val jsonFile = String(ResourceUtils.getFile("classpath:db.json").readBytes())
        val digimons: List<Digimon> = objectMapper.readValue(jsonFile)
        return digimons.associateBy { it.id }
    }

    @Bean
    fun digimonsByName(objectMapper: ObjectMapper): Map<String, Digimon> {
        val jsonFile = String(ResourceUtils.getFile("classpath:db.json").readBytes())
        val digimons: List<Digimon> = objectMapper.readValue(jsonFile)
        return digimons.associateBy { it.name }
    }

    @Bean
    fun digimonData(objectMapper: ObjectMapper): Map<Int, DigimonData> {
        val jsonFile = String(ResourceUtils.getFile("classpath:db.json").readBytes())
        val digimons: List<Digimon> = objectMapper.readValue(jsonFile)
        val digimonData = mutableMapOf<Int, DigimonData>()
        val digimonByName: Map<String, Digimon> = digimons.associateBy { it.name }

        digimons.forEach { d ->
            digimonData.put(d.id, DigimonData(d.id, d.name, d.attacks.map { it.id }.toSet(),
                    hashSetOf(d.id) + d.evolveFrom.map { p -> digimonByName[p]!!.id }.toSet(),
                    d.evolveTo.map { n -> digimonByName[n.to]!!.id }.toSet()))
        }
        return digimonData
    }

    @Bean
    fun attacksById(objectMapper: ObjectMapper, attackRepository: AttackRepository): Map<Int, Attack> {
        val jsonFile = String(ResourceUtils.getFile("classpath:attacks.json").readBytes())
        val readValue = objectMapper.readValue<Set<Attack>>(jsonFile)
        return readValue.associateBy { it.id }
    }

    @Bean
    public open fun attacksByName(objectMapper: ObjectMapper): Map<String, Attack> {
        val jsonFile = String(ResourceUtils.getFile("classpath:attacks.json").readBytes())
        return objectMapper.readValue<Set<Attack>>(jsonFile).associateBy { it.name }
    }
}