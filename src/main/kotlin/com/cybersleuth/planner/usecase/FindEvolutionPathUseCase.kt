package com.cybersleuth.planner.usecase

import com.cybersleuth.planner.domain.path.PathStep

interface FindEvolutionPathUseCase {
    fun execute(source: String, target: String? = null, wantedAttacks: Set<String>? = null): List<PathStep>
}