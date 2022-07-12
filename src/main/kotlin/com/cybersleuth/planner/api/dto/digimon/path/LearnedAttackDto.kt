package com.cybersleuth.planner.api.dto.digimon.path

import org.springframework.hateoas.RepresentationModel

class LearnedAttackDto(val id: Int, val name: String, val at: Int) : RepresentationModel<LearnedAttackDto>() {
}