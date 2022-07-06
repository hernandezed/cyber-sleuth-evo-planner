package com.cybersleuth.planner.business.bo

data class PathBo(val digimon: DigimonBo,
                  val learnedAttacks: Set<LearnAttackBo>,
                  val evolveAt: Int) {
}