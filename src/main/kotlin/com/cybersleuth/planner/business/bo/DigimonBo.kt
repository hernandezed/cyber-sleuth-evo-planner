package com.cybersleuth.planner.business.bo

data class DigimonBo(val id: Int,
                     val name: String,
                     val skill: SkillBo,
                     val stage: String,
                     val type: String,
                     val attribute: String,
                     val memory: Int,
                     val slots: Int,
                     val evolveFrom: Set<DegenerationBo>,
                     val evolutions: Set<EvolutionBo>,
                     val learnAttacks: Set<LearnAttackBo>,
                     val stats: Map<Int, StatsBo>) {
}