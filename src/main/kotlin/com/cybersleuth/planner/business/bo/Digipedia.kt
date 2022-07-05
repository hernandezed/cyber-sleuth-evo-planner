package com.cybersleuth.planner.business.bo

import com.cybersleuth.planner.database.Attack
import java.util.SortedSet


data class Digipedia(val digimons: Map<Int, DigimonBo>, val attacks: Map<Int, AttackBo>) {

    fun canLearn(digimonId: Int, attackId: Int): Boolean {
        return digimons[digimonId]!!.learnAttacks.any { it.attack.id == attackId }
    }

    fun canEvolve(digimonIdFrom: Int, digimonIdTo: Int): Boolean {
        return digimons[digimonIdFrom]!!.evolutions.map { it.to }.contains(digimonIdTo)
    }

    fun getAttackBy(digimonId: Int): Set<LearnAttackBo> {
        return digimons[digimonId]!!.learnAttacks
    }

    fun getNeighboursBy(digimonId: Int): Set<Int> {
        return setOf(digimons[digimonId]!!.id) + digimons[digimonId]!!.evolveFromIds.sortedBy { it } + digimons[digimonId]!!.evolutions.map { it.to }.sortedBy { it }
    }

}