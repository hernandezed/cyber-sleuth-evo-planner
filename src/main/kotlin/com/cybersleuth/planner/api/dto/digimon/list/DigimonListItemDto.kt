package com.cybersleuth.planner.api.dto.digimon.list

class DigimonListItemDto(val id: Int,
                         val name: String,
                         val hp: Int,
                         val sp: Int,
                         val attack: Int,
                         val defense: Int,
                         val intellect: Int,
                         val speed: Int,
                         val stage: String,
                         val type: String,
                         val attribute: String,
                         val memory: Int,
                         val slots: Int) {
}