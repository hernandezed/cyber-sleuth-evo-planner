package com.cybersleuth.planner.api.dto.detail

import com.cybersleuth.planner.business.bo.DegenerationBo
import com.cybersleuth.planner.business.bo.EvolutionBo
import com.cybersleuth.planner.business.bo.LearnAttackBo

class DigimonDetailDto(id: Int,
                       name: String,
                       mini: String,
                       val portrait: String,
                       val stage: String,
                       val type: String,
                       val memory: Int,
                       val slots: Int,
                       val evolveFrom: Set<DegenerationBo>,
                       val evolutions: Set<EvolutionDetailDto>,
                       val learnAttacks: Set<LearnedAttackDetailDto>) : MinimalDigimonDetailDto(id, name, mini) {
}