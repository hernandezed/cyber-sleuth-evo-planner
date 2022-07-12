package com.cybersleuth.planner.api.dto.digimon.detail

open class DigimonDetailDto(id: Int,
                            name: String,
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
                            val slots: Int,
                            val evolveFrom: Set<DegenerationDetailDto>,
                            val evolutions: Set<EvolutionDetailDto>,
                            val learnAttacks: Set<LearnedAttackDetailDto>) : MinimalDigimonDetailDto(id, name) {
}

