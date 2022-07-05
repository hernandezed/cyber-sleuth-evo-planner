package com.cybersleuth.planner.database.repositories

import com.cybersleuth.planner.database.Attack
import org.springframework.data.jpa.repository.JpaRepository
import java.util.SortedSet

interface AttackRepository : JpaRepository<Attack, Int> {

    fun findAllByNameIn(name: Set<String>): Set<Attack>
}