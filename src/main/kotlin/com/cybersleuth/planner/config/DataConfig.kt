package com.cybersleuth.planner.config

import com.cybersleuth.planner.domain.Digimon
import com.cybersleuth.planner.finder.model.DigimonData
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.util.ResourceUtils

@Configuration
class DataConfig(objectMapper: ObjectMapper) {
    private val digimonData: HashMap<Int, DigimonData>

    init {
        val jsonFile = String(ResourceUtils.getFile("classpath:db.json").readBytes())
        val digimons: List<Digimon> = objectMapper.readValue(jsonFile)

        val attackNames: MutableSet<String> = HashSet()
        val attackIdByNameMap: MutableMap<String, Int> = HashMap()
        digimonData = HashMap()
        val digimonByName: Map<String, Digimon> = digimons.associateBy { it.name }
        var attackCounter = 1
        digimons.forEach { d ->
            val moves: MutableSet<Int> = HashSet()
            d.attacks.forEach {
                if (it.inheritable) {
                    if (attackNames.add(it.move)) {
                        attackIdByNameMap[it.move] = attackCounter
                        moves.add(attackCounter)
                        attackCounter++
                    } else {
                        moves.add(attackIdByNameMap[it.move]!!)
                    }
                }
            }
            digimonData.put(d.id, DigimonData(d.id, d.name, moves,
                    hashSetOf(d.id) + d.evolveFrom.map { p -> digimonByName[p]!!.id }.toSet(),
                    d.evolveTo.map { n -> digimonByName[n.to]!!.id }.toSet()))
        }
    }

    @Bean
    fun digimonData(): Map<Int, DigimonData> {
        return digimonData
    }
}