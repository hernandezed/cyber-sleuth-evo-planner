package com.cybersleuth.planner.business.bo

import com.cybersleuth.planner.database.Attack


class Digipedia(val digimons: Map<Int, DigimonBo>, val attacks: Map<Int, AttackBo>) {

    fun canLearn(digimonId: Int, attackId: Int): Boolean {
        return digimons[digimonId]!!.learnAttacks.any { it.attackId == attackId }
    }

    fun canEvolve(digimonIdFrom: Int, digimonIdTo: Int): Boolean {
        return digimons[digimonIdFrom]!!.evolveToIds.contains(digimonIdTo)
    }

    fun isInheritableAttack(attackId: Int): Boolean {
        return attacks[attackId] != null || attacks[attackId]!!.inheritable
    }

    fun getAttackBy(digimonId: Int): Set<LearnAttackBo> {
        return digimons[digimonId]!!.learnAttacks
    }

    fun getNeighboursBy(digimonId: Int): Set<Int> {
        return setOf(digimons[digimonId]!!.id) + digimons[digimonId]!!.evolveFromIds.sortedBy { it } + digimons[digimonId]!!.evolveToIds.sortedBy { it }
    }
}