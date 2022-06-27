package com.cybersleuth.planner.finder

import com.cybersleuth.planner.CyberSleuthPlannerApplicationTests
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
        val result = instance.findShortestPath(source, target, setOf(13))
        assertThat(result).hasSize(2)
        assertThat(result.map { digimonData[it]!!.name })
                .containsExactlyElementsOf(mutableListOf("Kuramon", "Tsumemon"))
    }

    @Test
    fun findShortestPath_directEvolutionWithAttackFromOtherDigimon_returnPathWithSizeEight() {
        var source = 1
        var target = 10
        val result = instance.findShortestPath(source, target, setOf(83))
        assertThat(result).hasSize(8)
        assertThat(result.map { digimonData[it]!!.name })
                .containsExactlyElementsOf(mutableListOf("Kuramon",
                        "Pagumon",
                        "Gazimon",
                        "Leomon",
                        "MetalGreymon",
                        "Growlmon",
                        "Agumon (Blk)",
                        "Tsumemon"))
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
        assertThat(result).hasSize(5)
        assertThat(result.map { digimonData[it]!!.name })
                .containsExactlyElementsOf(mutableListOf("Kuramon", "Pagumon", "Lopmon", "MudFrigimon", "Palmon"))
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
        val result = instance.findShortestPath(source, target, setOf(20, 50, 25, 10, 100, 40))
        assertThat(result).hasSize(16)
        assertThat(result.map { digimonData[it]!!.name })
                .containsExactlyElementsOf(mutableListOf("Kuramon",
                        "Pagumon",
                        "Chuumon",
                        "Raremon",
                        "Gabumon (Blk)",
                        "Reppamon",
                        "Kudamon",
                        "Wanyamon",
                        "Gaomon",
                        "Togemon",
                        "Lalamon",
                        "Togemon",
                        "Palmon",
                        "Togemon",
                        "MachGaogamon",
                        "Merukimon"))
    }

    @Test
    fun findShortestPath_withInvalidAttack_returnEmptyPath() {
        var source = 1
        var target = 200
        val result = instance.findShortestPath(source, target, setOf(300000))
        assertThat(result).isEmpty()
    }


}