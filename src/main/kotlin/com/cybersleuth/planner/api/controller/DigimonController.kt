package com.cybersleuth.planner.api.controller

import com.cybersleuth.planner.api.dto.digimon.detail.*
import com.cybersleuth.planner.api.dto.digimon.list.DigimonListItemDto
import com.cybersleuth.planner.api.dto.digimon.path.DigimonPathInfoDto
import com.cybersleuth.planner.api.dto.digimon.path.LearnedAttackDto
import com.cybersleuth.planner.api.dto.digimon.path.PathDto
import com.cybersleuth.planner.business.bo.DigimonBo
import com.cybersleuth.planner.business.usecase.DigimonByIdUseCase
import com.cybersleuth.planner.business.usecase.FindAllDigimonsUseCase
import com.cybersleuth.planner.business.usecase.FindEvolutionShortestPath
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class DigimonController(val findEvolutionShortestPath: FindEvolutionShortestPath,
                        val findAllDigimonsUseCase: FindAllDigimonsUseCase,
                        val digimonByIdUseCase: DigimonByIdUseCase) {

    @GetMapping("/evolution")
    fun getPath(@RequestParam from: Int, @RequestParam(required = false) to: Int?,
                @RequestParam(name = "wantedAttack", required = false) wantedAttacks: Set<Int>?): List<PathDto> {
        return findEvolutionShortestPath.execute(from, to, wantedAttacks ?: setOf())
                .map {
                    val digimon = DigimonPathInfoDto(it.digimon.id, it.digimon.name)
                    digimon.add(
                            linkTo(
                                    methodOn(DigimonController::class.java)
                                            .getById(digimon.id)
                            ).withSelfRel())
                    val learnedAttacks = it.learnedAttacks.map { LearnedAttackDto(it.attack.id, it.attack.name, it.at).add(linkTo(methodOn(AttackController::class.java).getById(it.attack.id)).withSelfRel()) }.toSet()
                    PathDto(digimon, it.evolveAt, learnedAttacks)
                }
    }

    @CrossOrigin
    @GetMapping("/digimons")
    fun getAll(@RequestParam(required = false) attackId: Int?): Set<DigimonListItemDto> {
        val digimons = findAllDigimonsUseCase.execute(attackId)

        return digimons
                .map {
                    val stats = it.stats[1]!!
                    DigimonListItemDto(it.id, it.name, stats.hp, stats.sp, stats.attack, stats.defense, stats.intellect, stats.speed, it.stage, it.type, it.attribute, it.memory, it.slots)
                }.toSet()
    }

    @CrossOrigin
    @GetMapping("/digimons/{id}")
    fun getById(@PathVariable id: Int): DigimonDetailDto {
        val digimon: DigimonBo = digimonByIdUseCase.execute(id)
        return DigimonDetailDto(digimon.id, digimon.name, SkillDto(digimon.skill.name, digimon.skill.description), digimon.stage, digimon.type, digimon.attribute, digimon.memory, digimon.slots,
                digimon.evolveFrom.map { DegenerationDetailDto(it.id, it.name) }.toSet(),
                digimon.evolutions.map { EvolutionDetailDto(it.to, it.name, RequirementDto(it.requirements.level)) }.toSet(),
                digimon.learnAttacks.map { LearnedAttackDetailDto(AttackDetailDto(it.attack.id, it.attack.name, it.attack.attribute, it.attack.type, it.attack.cost, it.attack.power, it.attack.inheritable), it.at) }.toSet(),
                digimon.stats.mapValues { StatsDetailsDto(it.value.hp, it.value.sp, it.value.attack, it.value.defense, it.value.intellect, it.value.speed) })
    }


}