package com.cybersleuth.planner.api.dto.attack

import org.springframework.hateoas.RepresentationModel

open class AttackDto(val id: Int,
                     val name: String,
                     val attribute: String,
                     val type: String,
                     val cost: Int,
                     val power: Int,
                     val inheritable: Boolean): RepresentationModel<AttackDto>() {
}