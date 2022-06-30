package com.cybersleuth.planner.business.bo

import com.cybersleuth.planner.database.LearnAttack

class DigimonBo(val id: Int, val evolveFromIds: Set<Int>, val evolveToIds: Set<Int>, val learnAttacks: Set<LearnAttackBo>) {
}