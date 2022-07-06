package com.cybersleuth.planner.business.bo

data class DigimonBo(val id: Int,
                     val name: String,
                     val mini: String,
                     val portrait: String,
                     val stage: String,
                     val type: String,
                     val memory: Int,
                     val slots: Int,
                     val evolveFrom: Set<DegenerationBo>,
                     val evolutions: Set<EvolutionBo>,
                     val learnAttacks: Set<LearnAttackBo>) {
}