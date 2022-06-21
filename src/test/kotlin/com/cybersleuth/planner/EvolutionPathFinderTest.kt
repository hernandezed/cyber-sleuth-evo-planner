package com.cybersleuth.planner

import com.cybersleuth.planner.finder.EvolutionPathFinder
import com.cybersleuth.planner.finder.model.DigimonData
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class EvolutionPathFinderTest : CyberSleuthPlannerApplicationTests() {
    @Autowired
    lateinit var instance: EvolutionPathFinder

    @Autowired
    lateinit var digimonData: Map<Int, DigimonData>


    @Test
    fun findShortestPath_directEvolutionWithoutAttacks_returnPathWithSizeTwo() {
        var source = 1
        var target = 10
        val result = instance.findShortestPath(source, target, setOf())
        assertThat(result).hasSize(2)
        assertThat(result.map { digimonData[it]!!.name })
                .containsExactlyElementsOf(mutableListOf("Kuramon", "Tsumemon"))
    }

    @Test
    fun findShortestPath_directEvolutionWithAttackFromDirectEvolution_returnPathWithSizeTwo() {
        var source = 1
        var target = 10
        val result = instance.findShortestPath(source, target, setOf(5))
        assertThat(result).hasSize(2)
        assertThat(result.map { digimonData[it]!!.name })
                .containsExactlyElementsOf(mutableListOf("Kuramon", "Tsumemon"))
    }

    @Test
    fun findShortestPath_directEvolutionWithAttackFromOtherDigimon_returnPathWithSizeEight() {
        var source = 1
        var target = 10
        val result = instance.findShortestPath(source, target, setOf(30))
        assertThat(result).hasSize(8)
        assertThat(result.map { digimonData[it]!!.name })
                .containsExactlyElementsOf(mutableListOf("Kuramon", "Tsumemon", "Agumon (Blk)", "Monochromon", "Hackmon", "PlatinumSukamon",
                        "Keramon", "Tsumemon"))
    }

    @Test
    fun findShortestPath_withNoDirectEvolution_returnPathOfSize() {
        var source = 1
        var target = 284
        val result = instance.findShortestPath(source, target, setOf())
        assertThat(result).hasSize(6)
        assertThat(result.map { digimonData[it]!!.name })
                .containsExactlyElementsOf(mutableListOf("Kuramon", "Pagumon", "Impmon", "Socerimon", "Piximon", "Hououmon"))
    }
}