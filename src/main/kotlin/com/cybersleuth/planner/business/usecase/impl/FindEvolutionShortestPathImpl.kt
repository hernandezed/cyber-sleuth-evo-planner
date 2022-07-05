package com.cybersleuth.planner.business.usecase.impl

import com.cybersleuth.planner.business.bo.DigimonBo
import com.cybersleuth.planner.business.bo.Digipedia
import com.cybersleuth.planner.business.bo.PathBo
import com.cybersleuth.planner.business.usecase.FindEvolutionShortestPath
import com.cybersleuth.planner.finder.EvolutionPathFinder
import com.cybersleuth.planner.ports.AttackPort
import com.cybersleuth.planner.ports.DigimonPort
import org.springframework.stereotype.Component

@Component
class FindEvolutionShortestPathImpl(val pathFinder: EvolutionPathFinder,
                                    val digimonPort: DigimonPort, val attackPort: AttackPort) : FindEvolutionShortestPath {
    override fun execute(sourceId: Int, targetId: Int?, wantedAttacks: Set<Int>): List<PathBo> {
        val wantedAttacksBos = attackPort.findByIds(wantedAttacks).toMutableSet()
        val wantedAttacksIds = wantedAttacksBos.map { it.id }.toSet()

        val evolutionPath = pathFinder.findShortestPath(sourceId, targetId, wantedAttacksIds)
        val evolutionParticipants = digimonPort.findByIds(evolutionPath.toSet())
                .associateBy { it.id }

        val path: MutableList<PathBo> = mutableListOf()
        for (i in evolutionPath.indices) {
            val pathParticipantSource = evolutionParticipants[evolutionPath[i]]!!
            val pathParticipantTarget = if (i != evolutionPath.lastIndex) evolutionParticipants[evolutionPath[i + 1]] else null
            val evolveLevel = evolveLevel(pathParticipantSource, pathParticipantTarget)
            val learnAttackBos = pathParticipantSource.learnAttacks.filter { wantedAttacksIds.contains(it.attack.id) }.toSet()
            wantedAttacksBos.removeAll(learnAttackBos.map { it.attack }.toSet())
            val maxAttackLevel: Int = learnAttackBos.maxByOrNull { it.at }?.at ?: 0
            var pathLevel: Int = evolveLevel
            if (maxAttackLevel > evolveLevel) {
                pathLevel = maxAttackLevel
            }
            path.add(PathBo(pathParticipantSource, learnAttackBos, pathLevel))
        }
        return path
    }


    private fun evolveLevel(source: DigimonBo, target: DigimonBo?): Int {
        return if (target == null) 0
        else if (source.evolveFromIds.contains(target.id)) 0
        else
            source.evolutions.first { it.to == target.id }.requirements.level!!
    }
}