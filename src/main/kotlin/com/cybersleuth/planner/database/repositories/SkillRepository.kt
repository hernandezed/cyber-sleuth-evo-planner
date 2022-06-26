package com.cybersleuth.planner.database.repositories

import com.cybersleuth.planner.database.Skill
import org.springframework.data.jpa.repository.JpaRepository

interface SkillRepository : JpaRepository<Skill, Int> {
}