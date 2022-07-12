package com.cybersleuth.planner.api.controller

import com.cybersleuth.planner.api.dto.attack.detail.AttackDetailDto
import com.cybersleuth.planner.api.dto.attack.list.AttackListItemDto
import com.cybersleuth.planner.business.usecase.AttackByIdUseCase
import com.cybersleuth.planner.business.usecase.FindAllAttacksUseCase
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/attacks")
class AttackController(val findAllAttacksUseCase: FindAllAttacksUseCase, val attackByIdUseCase: AttackByIdUseCase) {

    @GetMapping
    fun findAll(): List<AttackListItemDto> {
        return findAllAttacksUseCase.execute().map {
            AttackListItemDto(it.name, it.inheritable)
                    .add(
                            linkTo(
                                    methodOn(AttackController::class.java)
                                            .getById(it.id)
                            ).withSelfRel())
        }
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): AttackDetailDto {
        val attack = attackByIdUseCase.execute(id)
        return AttackDetailDto(attack.id, attack.name, attack.inheritable)
    }
}