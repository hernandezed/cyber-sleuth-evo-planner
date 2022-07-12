package com.cybersleuth.planner.finder

import com.cybersleuth.planner.CyberSleuthPlannerApplicationTests
import com.cybersleuth.planner.database.repositories.DigimonRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.awt.Image
import java.io.BufferedInputStream
import java.io.File
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import javax.imageio.ImageIO
import javax.transaction.Transactional


class EvolutionPathFinderTest : CyberSleuthPlannerApplicationTests() {
    @Autowired
    lateinit var instance: EvolutionPathFinder

    @Autowired
    lateinit var digimonRepository: DigimonRepository

    /*  @Test
      fun a() {
          val digimons = digimonRepository.findAll()

          digimons.forEach {
              val inMini = BufferedInputStream(URL(it.mini).openStream())
              val inPort = BufferedInputStream(URL(it.portrait).openStream())
              if (Files.copy(inMini, Paths.get("/Users/a309880/Downloads/cyber-sleuth-planner/src/test/resources/minis/" + it.id + ".png"), StandardCopyOption.REPLACE_EXISTING) == 0L) {
                  println("mini: " + it.id)
              }
                  if (Files.copy(inPort, Paths.get("/Users/a309880/Downloads/cyber-sleuth-planner/src/test/resources/portraits/" + it.id + ".png"), StandardCopyOption.REPLACE_EXISTING) == 0L) {
                  println("port: " + it.id)
              }
          }
      }*/

    @Test
    fun findShortestPath_directEvolutionWithoutAttacks_returnPathWithSizeTwo() {
        var source = 1
        var target = 10
        val result = instance.findShortestPath(source, target, setOf())
        assertThat(result).hasSize(2)
        /*assertThat(result.map { digimonData[it]!!.name })
                .containsExactlyElementsOf(mutableListOf("Kuramon", "Tsumemon"))*/
    }

    @Test
    fun findShortestPath_directEvolutionWithAttackFromDirectEvolution_returnPathWithSizeTwo() {
        var source = 1
        var target = 10
        val result = instance.findShortestPath(source, target, setOf(13))
        assertThat(result).hasSize(2)
        /*assertThat(result.map { digimonData[it]!!.name })
                .containsExactlyElementsOf(mutableListOf("Kuramon", "Tsumemon"))*/
    }

    @Test
    fun findShortestPath_directEvolutionWithAttackFromOtherDigimon_returnPathWithSizeEight() {
        var source = 1
        var target = 10
        val result = instance.findShortestPath(source, target, setOf(83))
        assertThat(result).hasSize(8)
        /*assertThat(result.map { digimonData[it]!!.name })
                .containsExactlyElementsOf(mutableListOf("Kuramon",
                        "Pagumon",
                        "Gazimon",
                        "Leomon",
                        "MetalGreymon",
                        "Growlmon",
                        "Agumon (Blk)",
                        "Tsumemon"))*/
    }

    @Test
    fun findShortestPath_withNoDirectEvolution_returnPathOfSizeSix() {
        var source = 1
        var target = 284
        val result = instance.findShortestPath(source, target, setOf())
        assertThat(result).hasSize(6)
        /*assertThat(result.map { digimonData[it]!!.name })
                .containsExactlyElementsOf(mutableListOf("Kuramon", "Pagumon", "Impmon", "Socerimon", "Piximon", "Hououmon"))*/
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
        /* assertThat(result.map { digimonData[it]!!.name })
                 .containsExactlyElementsOf(mutableListOf("Kuramon", "Pagumon", "Lopmon", "MudFrigimon", "Palmon"))*/
    }


    @Test
    fun findShortestPath_withInvalidTarget_returnEmptyPath() {
        var source = 1
        var target = 1000
        val result = instance.findShortestPath(source, target, setOf(20))
        assertThat(result).isEmpty()
    }

    @Test
    @Transactional
    fun findShortestPath_withMultipleAttacks_returnPathSize() {
        var source = 1
        var target = 300
        val result = instance.findShortestPath(source, target, setOf(20, 49, 26, 10, 102, 40))
        val ex = digimonRepository.findAll().filter { result.contains(it.id) }
        assertThat(result).hasSize(14)
        /* assertThat(result.map { digimonData[it]!!.name })
                 .containsExactlyElementsOf(mutableListOf("Kuramon",
                         "Pagumon",
                         "Lopmon",
                         "Devimon",
                         "Lucemon",
                         "Angemon",
                         "Kudamon",
                         "Wanyamon",
                         "Gaomon",
                         "Togemon",
                         "Palmon",
                         "Togemon",
                         "MachGaogamon",
                         "Merukimon"))*/
    }

    @Test
    fun findShortestPath_withInvalidAttack_returnEmptyPath() {
        var source = 1
        var target = 200
        val result = instance.findShortestPath(source, target, setOf(300000))
        assertThat(result).isEmpty()
    }


}