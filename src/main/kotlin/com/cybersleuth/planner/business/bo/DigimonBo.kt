package com.cybersleuth.planner.business.bo

import com.cybersleuth.planner.database.LearnAttack

data class DigimonBo(val id: Int, val name:String, val evolveFromIds: Set<Int>, val evolutions: Set<EvolutionBo>, val learnAttacks: Set<LearnAttackBo>) {
}