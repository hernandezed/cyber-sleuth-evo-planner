package com.cybersleuth.planner.database.repositories

import com.cybersleuth.planner.database.Digimon
import org.springframework.data.jpa.repository.JpaRepository

interface DigimonRepository : JpaRepository<Digimon, Int> {
}