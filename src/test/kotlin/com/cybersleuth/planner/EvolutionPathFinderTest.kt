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
    fun findShortestPath_withNoDirectEvolution_returnPathOfSizeSix() {
        var source = 1
        var target = 284
        val result = instance.findShortestPath(source, target, setOf())
        assertThat(result).hasSize(6)
        assertThat(result.map { digimonData[it]!!.name })
                .containsExactlyElementsOf(mutableListOf("Kuramon", "Pagumon", "Impmon", "Socerimon", "Piximon", "Hououmon"))
    }

    @Test
    fun findShortestPath_withoutTragetAndWithoutAttacks_returnEmptyPath() {
        var source = 1
        var target = null
        val result = instance.findShortestPath(source, target, setOf())
        assertThat(result).hasSize(0)
    }

    @Test
    fun findShortestPath_withoutTragetAndWithAttacks_returnPathSize4() {
        var source = 1
        var target = null
        val result = instance.findShortestPath(source, target, setOf(20))
        assertThat(result).hasSize(4)
        assertThat(result.map { digimonData[it]!!.name })
                .containsExactlyElementsOf(mutableListOf("Kuramon", "Pagumon", "Lopmon", "Gargomon"))
    }


    @Test
    fun findShortestPath_withInvalidTarget_returnEmptyPath() {
        var source = 1
        var target = 1000
        val result = instance.findShortestPath(source, target, setOf(20))
        assertThat(result).isEmpty()
    }

    @Test
    fun findShortestPath_withMultipleAttacks_returnPathSize() {
        var source = 1
        var target = 300
        val result = instance.findShortestPath(source, target, setOf(20, 50, 25, 10, 100, 40, 60))
        assertThat(result).hasSize(16)
        assertThat(result.map { digimonData[it]!!.name })
                .containsExactlyElementsOf(mutableListOf("Kuramon", "Pagumon", "Lopmon", "Gargomon", "Gaomon", "Ogremon",
                        "Gaomon", "Togemon", "Palmon", "Vegiemon", "Betamon", "Bukamon", "Syakomon", "Ikkakumon",
                        "MachGaogamon", "Merukimon"))
    }

    @Test
    fun findShortestPath_withInvalidAttack_returnEmptyPath() {
        var source = 1
        var target = 200
        val result = instance.findShortestPath(source, target, setOf(300000))
        assertThat(result).isEmpty()
    }


}