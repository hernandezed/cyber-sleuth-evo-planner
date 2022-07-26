package com.cybersleuth.planner.api.controller

import com.cybersleuth.planner.api.dto.attack.AttackDto
import com.cybersleuth.planner.business.usecase.AttackByIdUseCase
import com.cybersleuth.planner.business.usecase.FindAllAttacksUseCase
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/attacks")
class AttackController(val findAllAttacksUseCase: FindAllAttacksUseCase, val attackByIdUseCase: AttackByIdUseCase) {

    @CrossOrigin
    @GetMapping
    fun findAll(): List<AttackDto> {
        return findAllAttacksUseCase.execute().map {
            AttackDto(it.id, it.name, it.attribute, it.type, it.cost, it.power, it.inheritable)
                    .add(
                            linkTo(
                                    methodOn(AttackController::class.java)
                                            .getById(it.id)
                            ).withSelfRel())
        }
    }

    @CrossOrigin
    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): AttackDto {
        val attack = attackByIdUseCase.execute(id)
        val attackDto = AttackDto(attack.id, attack.name, attack.attribute, attack.type, attack.cost, attack.power, attack.inheritable)
        return attackDto.add(linkTo(
                methodOn(AttackController::class.java)
                        .getById(attackDto.id)
        ).withSelfRel())
                .add(linkTo(
                        methodOn(DigimonController::class.java)
                                .getAll(attackDto.id)
                ).withRel("learnedBy"))
    }

    /*
    ,

     */
}