package com.cybersleuth.planner.business.usecase

import com.cybersleuth.planner.business.bo.PathBo

interface FindEvolutionShortestPath {
    fun execute(from: Int, to: Int?, wantedAttacks: Set<Int>): List<PathBo>
}