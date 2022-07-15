package com.cybersleuth.planner.api.dto.attack.detail

open class AttackDetailDto(val id: Int,
                           val name: String,
                           val attribute: String,
                           val type: String,
                           val cost: Int,
                           val power: Int,
                           val inheritable: Boolean) {
}