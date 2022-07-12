package com.cybersleuth.planner.api.dto.attack.list

import org.springframework.hateoas.RepresentationModel

open class AttackListItemDto(val name: String, val inheritable: Boolean) : RepresentationModel<AttackListItemDto>() {
}