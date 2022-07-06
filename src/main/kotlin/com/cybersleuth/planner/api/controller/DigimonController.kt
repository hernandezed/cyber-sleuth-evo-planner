package com.cybersleuth.planner.api.controller

import com.cybersleuth.planner.business.bo.DigimonBo
import com.cybersleuth.planner.business.bo.PathBo
import com.cybersleuth.planner.business.usecase.FindAllDigimonsUseCase
import com.cybersleuth.planner.business.usecase.FindEvolutionShortestPath
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class DigimonController(val findEvolutionShortestPath: FindEvolutionShortestPath,
                        val findAllDigimonsUseCase: FindAllDigimonsUseCase) {

    @GetMapping("/evolution")
    fun getPath(@RequestParam from: Int, @RequestParam(required = false) to: Int?,
                @RequestParam(name = "wantedAttack", required = false) wantedAttacks: Set<Int>?): List<PathBo> {
        return findEvolutionShortestPath.execute(from, to, wantedAttacks ?: setOf())
    }

    @GetMapping("/digimons")
    fun getAll(): Set<DigimonBo> {
        return findAllDigimonsUseCase.execute()
    }
}