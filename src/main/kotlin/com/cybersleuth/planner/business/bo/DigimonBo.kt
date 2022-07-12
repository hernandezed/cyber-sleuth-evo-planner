package com.cybersleuth.planner.business.bo

data class DigimonBo(val id: Int,
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
                     val slots: Int,
                     val evolveFrom: Set<DegenerationBo>,
                     val evolutions: Set<EvolutionBo>,
                     val learnAttacks: Set<LearnAttackBo>) {
}