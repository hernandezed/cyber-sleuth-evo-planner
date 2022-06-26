package com.cybersleuth.planner.database.repositories

import com.cybersleuth.planner.database.Attack
import org.springframework.data.jpa.repository.JpaRepository

interface AttackRepository : JpaRepository<Attack, Int> {
}