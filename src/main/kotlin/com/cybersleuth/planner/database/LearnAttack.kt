package com.cybersleuth.planner.database

import javax.persistence.*

@Entity
class LearnAttack(
        @Id
        @GeneratedValue
        val id: Int?,
        @ManyToOne
        val learnedBy: Digimon,
        @ManyToOne(cascade = [CascadeType.ALL])
        val learnedAttack: Attack,
        val at: Int
) {
}