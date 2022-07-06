package com.cybersleuth.planner.business.bo

data class DigimonBo(val id: Int,
                     val name: String,
                     val evolveFrom: Set<DegenerationBo>,
                     val evolutions: Set<EvolutionBo>,
                     val learnAttacks: Set<LearnAttackBo>) {
}