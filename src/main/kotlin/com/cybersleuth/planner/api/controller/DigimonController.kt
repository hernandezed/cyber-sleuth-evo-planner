package com.cybersleuth.planner.api.controller

import com.cybersleuth.planner.api.dto.list.DigimonListItemDto
import com.cybersleuth.planner.api.dto.path.DigimonPathInfoDto
import com.cybersleuth.planner.api.dto.path.LearnedAttackDto
import com.cybersleuth.planner.api.dto.path.PathDto
import com.cybersleuth.planner.business.usecase.FindAllDigimonsUseCase
import com.cybersleuth.planner.business.usecase.FindEvolutionShortestPath
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class DigimonController(val findEvolutionShortestPath: FindEvolutionShortestPath,
                        val findAllDigimonsUseCase: FindAllDigimonsUseCase) {

    @GetMapping("/evolution")
    fun getPath(@RequestParam from: Int, @RequestParam(required = false) to: Int?,
                @RequestParam(name = "wantedAttack", required = false) wantedAttacks: Set<Int>?): List<PathDto> {
        return findEvolutionShortestPath.execute(from, to, wantedAttacks ?: setOf())
                .map { PathDto(DigimonPathInfoDto(it.digimon.id, it.digimon.name, it.digimon.mini, it.digimon.portrait), it.evolveAt, it.learnedAttacks.map { LearnedAttackDto(it.attack.id, it.attack.name, it.at) }.toSet()) }
    }

    @GetMapping("/digimons")
    fun getAll(): Set<DigimonListItemDto> {
        return findAllDigimonsUseCase.execute()
                .map { DigimonListItemDto(it.id, it.name, it.mini, it.portrait, it.stage, it.type, it.memory, it.slots) }
                .toSet()
    }

    @GetMapping("/digimons/{id}")
    fun getById(@PathVariable id: Int) {

    }
}