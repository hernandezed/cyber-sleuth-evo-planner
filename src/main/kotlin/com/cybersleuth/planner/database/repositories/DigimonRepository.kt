package com.cybersleuth.planner.database.repositories

import com.cybersleuth.planner.database.Digimon
import org.springframework.data.jpa.repository.JpaRepository

interface DigimonRepository : JpaRepository<Digimon, Int> {
    fun findAllByEvolveFromName(name: String)
    fun findAllByName(name: String): Digimon
    fun findAllByLearnedAttacksLearnedAttackId(id: Int): Set<Digimon>
}