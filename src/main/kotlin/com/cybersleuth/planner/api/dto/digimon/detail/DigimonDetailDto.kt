package com.cybersleuth.planner.api.dto.digimon.detail

open class DigimonDetailDto(id: Int,
                            name: String,
                            val stage: String,
                            val type: String,
                            val attribute: String,
                            val memory: Int,
                            val slots: Int,
                            val evolveFrom: Set<DegenerationDetailDto>,
                            val evolutions: Set<EvolutionDetailDto>,
                            val learnAttacks: Set<LearnedAttackDetailDto>,
                            val stats: Map<Int, StatsDetailsDto>) : MinimalDigimonDetailDto(id, name) {
}

