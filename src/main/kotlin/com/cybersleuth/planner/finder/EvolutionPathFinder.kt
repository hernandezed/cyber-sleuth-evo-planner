package com.cybersleuth.planner.finder

import com.cybersleuth.planner.business.usecase.FindAllDigimonsUseCase
import com.cybersleuth.planner.business.usecase.FindAllGroupedByInheritableAttacksUseCase
import com.cybersleuth.planner.database.Digimon
import org.springframework.stereotype.Component
import java.util.*
import javax.transaction.Transactional
import kotlin.collections.HashMap

@Component
class EvolutionPathFinder {
    val attackToDigis: Map<Int, List<Digimon>>
    val findAllDigimonsUseCase: FindAllDigimonsUseCase

    constructor(findAllDigimonsUseCase: FindAllDigimonsUseCase, findAllGroupedByInheritableAttacksUseCase: FindAllGroupedByInheritableAttacksUseCase) {
        this.findAllDigimonsUseCase = findAllDigimonsUseCase
        attackToDigis = findAllGroupedByInheritableAttacksUseCase.execute()
    }

    @Transactional
    fun findShortestPath(source: Int, target: Int?, wantedAttacks: Set<Int>): List<Int> {
        val digimons: Map<Int, Digimon> = findAllDigimonsUseCase.execute().associateBy { it.id }
        val path: MutableList<Int> = mutableListOf(source)
        val skillPath: MutableList<Int> = mutableListOf()
        val skillContext: MutableMap<Int, Boolean> = HashMap()
        wantedAttacks.associateWithTo(skillContext) { false }
        var shortestPath: MutableList<Int>
        try {
            if (skillContext.isNotEmpty()) {
                skillContext.keys.forEach { id ->
                    run {
                        if (attackToDigis[id] != null) {
                            skillPath.add(id)
                            skillContext[id] = true
                        }
                    }
                }
                val fullSkillPaths: MutableList<MutableList<Int>> = LinkedList<MutableList<Int>>()
                val posiblePaths = permutations(skillPath)
                for (posiblePath in posiblePaths) {
                    var currentSource = source
                    var currentSkillPath = mutableListOf<Int>()
                    val coveredSkills = mutableMapOf<Int, Boolean>()
                    for (elementPath in posiblePath) {
                        if (!coveredSkills.getOrDefault(elementPath)) {
                            val skillNodePath = findClosestSkillHolderPath(currentSource, elementPath, skillContext)
                            currentSource = skillNodePath[skillNodePath.size - 1]
                            val moves = digimons[currentSource]!!.learnedAttacks
                            for (move in moves) {
                                coveredSkills[move.learnedAttack.id] = true
                            }
                            currentSkillPath = (currentSkillPath + skillNodePath.slice(0 until skillNodePath.size - 1)).toMutableList()
                        }
                    }
                    if (target != null) {
                        currentSkillPath = (currentSkillPath + findRoute(listOf(currentSource, target), null, mapOf())).toMutableList()
                    } else {
                        currentSkillPath.add(currentSource)
                    }
                    fullSkillPaths.add(currentSkillPath)
                }
                shortestPath = fullSkillPaths[0]
                for (i in 1 until fullSkillPaths.size) {
                    if (shortestPath.size > fullSkillPaths[i].size) {
                        shortestPath = fullSkillPaths[i]
                    }
                }
            } else if (target != null) {
                shortestPath = path
                shortestPath.add(target)
                shortestPath = findRoute(shortestPath, null, mapOf())
            } else {
                shortestPath = mutableListOf()
            }
        } catch (e: Exception) {
            shortestPath = mutableListOf()
        }
        return shortestPath
    }

    private fun findClosestSkillHolderPath(source: Int, skill: Int, skillContext: Map<Int, Boolean>): MutableList<Int> {
        return findRoute(listOf(source, null), skill, skillContext)
    }

    private fun findRoute(path: List<Int?>, attack: Int?, wantedAttacks: Map<Int, Boolean>): MutableList<Int> {
        val digimons: Map<Int, Digimon> = findAllDigimonsUseCase.execute().associateBy { it.id }
        val source = path[0]!!
        var target = path[1]
        val stack: Queue<Int> = LinkedList()
        val pathToSource = HashMap<Int, MutableList<Int>>()
        pathToSource[source] = ArrayList()
        val visited = HashMap<Int, Boolean>()
        var pathFound = false
        stack.add(source)
        while (stack.isNotEmpty() && !pathFound) {
            val current = stack.poll()
            val neighbours: Set<Int> = setOf(digimons[current]!!.id) + digimons[current]!!.evolveFrom.map { it.id }.toSet().sortedBy { it } + digimons[current]!!.evolveTo.map { it.to.id }.toSet().sortedBy { it }
            var ctr = 0
            var attackCandidate = 0
            var attackCandidateRating = 0
            while (ctr < neighbours.size && !pathFound) {
                val neighbourId = neighbours.elementAt(ctr)
                if (!visited.getOrDefault(neighbourId) && neighbourId != current) {
                    if (attack != null && attackToDigis[attack]!!.filter { it.id == neighbourId }.any()) {
                        var rating = 0
                        val attacks = digimons[neighbourId]!!.learnedAttacks

                        for (k in attacks.indices) {
                            if (wantedAttacks[attacks.elementAt(k).learnedAttack.id] != null) {
                                rating++
                            }
                        }

                        if (rating > attackCandidateRating) {
                            attackCandidateRating = rating;
                            attackCandidate = neighbourId;
                        }
                    }
                    if (neighbourId == target) {
                        pathFound = true
                    } else {
                        stack.add(neighbourId)
                    }
                    pathToSource[neighbourId] = pathToSource[current]!!.toMutableList()
                    pathToSource[neighbourId]!!.add(current)
                }
                ctr++
            }
            if (attackCandidate > 0) {
                target = attackCandidate
                pathFound = true
            }
            visited[current] = true
        }
        if (pathToSource[target] == null) {
            throw NoSuchElementException()
        }
        pathToSource[target]!!.add(target!!)
        return pathToSource[target]!!
    }

    private fun permutations(input: List<Int>): List<List<Int>> {
        val solutions = mutableListOf<List<Int>>()
        permutationsRecursive(input, 0, solutions)
        return solutions
    }

    private fun permutationsRecursive(input: List<Int>, index: Int, answers: MutableList<List<Int>>) {
        if (index == input.lastIndex) answers.add(input.toList())
        for (i in index..input.lastIndex) {
            Collections.swap(input, index, i)
            permutationsRecursive(input, index + 1, answers)
            Collections.swap(input, i, index)
        }
    }


    fun MutableMap<Int, Boolean>.getOrDefault(key: Int): Boolean {
        return if (this[key] != null) {
            this[key]!!
        } else {
            false
        }
    }

}