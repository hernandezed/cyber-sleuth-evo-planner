package com.cybersleuth.planner.database

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class DigimonStats(
        @Id
        val id: Int,
        val hp: Int,
        val sp: Int,
        val attack: Int,
        val defense: Int,
        val intellect: Int,
        val speed: Int,
        @ManyToOne
        val digimon: Digimon,
        val level: Int
) {
}