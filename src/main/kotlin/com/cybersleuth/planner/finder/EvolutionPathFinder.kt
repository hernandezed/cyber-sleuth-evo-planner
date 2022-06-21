package com.cybersleuth.planner.finder

import com.cybersleuth.planner.finder.model.DigimonData
import org.springframework.stereotype.Component
import java.util.*
import kotlin.collections.HashMap

@Component
class EvolutionPathFinder {
    val digimonData: Map<Int, DigimonData>
    val skillToDigis: Map<Int, MutableMap<Int, Boolean>>

    constructor(digimonData: Map<Int, DigimonData>) {
        this.digimonData = digimonData
        skillToDigis = HashMap()
        this.digimonData.values.forEach { d ->
            d.attacks.forEach { a ->
                run {
                    if (skillToDigis[a] == null) {
                        if (a == 6 || a == 120) {
                            println()
                        }
                        (skillToDigis as HashMap<Int, MutableMap<Int, Boolean>>)[a] = HashMap()
                    }
                    (skillToDigis as HashMap<Int, MutableMap<Int, Boolean>>)[a]!![d.id] = true
                }
            }
        }
    }

    fun findShortestPath(source: Int, target: Int, wantedAttacks: Set<Int>): List<Int> {
        var path: MutableList<Int> = mutableListOf(source)
        var skillPath: MutableList<Int> = mutableListOf()
        var skillContext: MutableMap<Int, Boolean> = HashMap()
        wantedAttacks.associateWithTo(skillContext) { false }
        var shortestPath: MutableList<Int> = mutableListOf()
        try {
            if (skillContext.isNotEmpty()) {
                skillContext.keys.forEach { id ->
                    run {
                        if (skillToDigis[id] != null || (skillToDigis[id]!![target] != null && skillToDigis[id]!![source] != null)) {
                            skillPath.add(id)
                            skillContext[id] = true
                        }
                    }
                }
                var fullSkillPaths: MutableList<MutableList<Int>> = LinkedList<MutableList<Int>>()
                var posiblePaths = permutations(skillPath)
                for (i in 0 until posiblePaths.size) {
                    var currentSkills = posiblePaths[i]
                    var currentSource = source
                    var currentSkillPath = mutableListOf<Int>()
                    var coveredSkills = mutableMapOf<Int, Boolean>()
                    for (j in 0 until currentSkills.size) {
                        if (!coveredSkills.getOrDefault(currentSkills[j])) {
                            var skillNodePath = findClosestSkillHolderPath(currentSource, currentSkills[j], null, skillContext)
                            currentSource = skillNodePath[skillNodePath.size - 1]
                            var moves = digimonData[currentSource]!!.attacks
                            for (k in 0 until moves.size) {
                                coveredSkills[moves.elementAt(k)] = true
                            }
                            currentSkillPath = (currentSkillPath + skillNodePath.slice(0 until skillNodePath.size - 1)).toMutableList()
                        }
                    }
                    if (target != -1) {
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
            } else if (target != -1) {
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

    private fun findClosestSkillHolderPath(source: Int, skill: Int, target: Int?, skillContext: Map<Int, Boolean>): MutableList<Int> {
        return findRoute(listOf(source, target), skill, skillContext)
    }

    private fun findRoute(path: List<Int?>, attack: Int?, wantedAttacks: Map<Int, Boolean>): MutableList<Int> {
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
            val neighbours: Set<Int> = digimonData[current]!!.prev + digimonData[current]!!.next
            var ctr = 0
            var attackCandidate = 0
            var attackCandidateRating = 0
            while (ctr < neighbours.size && !pathFound) {
                val neighbourId = neighbours.elementAt(ctr)
                if (!visited.getOrDefault(neighbourId) && neighbourId != current) {
                    if (attack != null && skillToDigis[attack]!!.getOrDefault(neighbourId)) {
                        var rating = 0
                        val attacks = digimonData[neighbourId]!!.attacks

                        for (k in attacks.indices) {
                            if (wantedAttacks[attacks.elementAt(k)] != null) {
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
            throw java.util.NoSuchElementException()
        }
        pathToSource[target]!!.add(target!!)
        return pathToSource[target]!!
    }


    fun List<Int>.permute(input: List<Int>) {
        permutations(input)
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