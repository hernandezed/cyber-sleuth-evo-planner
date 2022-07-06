package com.cybersleuth.planner.api.dto.path

class PathDto(val digimon: DigimonPathInfoDto,
              val evolveAt: Int,
              val learnedAttacks: Set<LearnedAttackDto>
) {
}