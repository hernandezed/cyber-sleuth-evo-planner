package com.cybersleuth.planner.finder

import com.cybersleuth.planner.business.bo.Digipedia
import kotlinx.coroutines.*
import org.springframework.stereotype.Component
import java.util.*
import kotlin.collections.HashMap

@Component
class EvolutionPathFinder(val digipedia: Digipedia) {

    fun findShortestPath(source: Int, target: Int?, wantedAttacks: Set<Int>): List<Int> {
        val path: MutableList<Int> = mutableListOf(source)
        var shortestPath: MutableList<Int>
        try {
            if (wantedAttacks.isNotEmpty()) {
                val fullSkillPaths: MutableList<MutableList<Int>> = LinkedList<MutableList<Int>>()

                runBlocking {
                    wantedAttacks.toMutableList().permute().map { posiblePath ->
                        launch {
                            var currentSource = source
                            var currentSkillPath = mutableListOf<Int>()
                            val covered: SortedSet<Int> = TreeSet()
                            for (elementPath in posiblePath) {
                                if (!covered.contains(elementPath)) {
                                    val skillNodePath = findRoute(listOf(currentSource, null), elementPath, wantedAttacks)
                                    currentSource = skillNodePath[skillNodePath.lastIndex]
                                    covered.addAll(digipedia.getAttackBy(currentSource).map { it.attack.id })
                                    currentSkillPath = (currentSkillPath + skillNodePath.slice(0 until skillNodePath.size - 1)).toMutableList()
                                }
                            }
                            if (target != null) {
                                currentSkillPath = (currentSkillPath + findRoute(listOf(currentSource, target), null, setOf())).toMutableList()
                            } else {
                                currentSkillPath.add(currentSource)
                            }
                            fullSkillPaths.add(currentSkillPath)
                        }
                    }.joinAll()
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
                shortestPath = findRoute(shortestPath, null, setOf())
            } else {
                shortestPath = mutableListOf()
            }
        } catch (e: Exception) {
            shortestPath = mutableListOf()
        }
        return shortestPath
    }

    private fun findRoute(path: List<Int?>, attack: Int?, wantedAttacks: Set<Int>): MutableList<Int> {
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
            val neighbours: Set<Int> = digipedia.getNeighboursBy(current)
            var ctr = 0
            var attackCandidate = 0
            var attackCandidateRating = 0
            while (ctr < neighbours.size && !pathFound) {
                val neighbourId = neighbours.elementAt(ctr)
                if (!visited.getOrDefault(neighbourId) && neighbourId != current) {
                    if (attack != null && digipedia.canLearn(neighbourId, attack)) {
                        var rating = 0
                        val attacks = digipedia.getAttackBy(neighbourId)

                        for (k in attacks) {
                            if (wantedAttacks.contains(k.attack.id)) {
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

    fun List<Int>.permute(): Set<List<Int>> {
        val solutions = mutableSetOf<List<Int>>()
        permutationsRecursive(this, 0, solutions)
        return solutions
    }

    private fun permutationsRecursive(input: List<Int>, index: Int, answers: MutableSet<List<Int>>) {
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