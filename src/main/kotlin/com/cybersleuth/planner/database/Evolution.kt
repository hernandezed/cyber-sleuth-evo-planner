package com.cybersleuth.planner.database

import javax.persistence.*

@Entity
class Evolution(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Int?,
        @OneToOne
        val from: Digimon,
        @OneToOne
        val to: Digimon,
        @Embedded
        val requirements: Requirements
) {
}