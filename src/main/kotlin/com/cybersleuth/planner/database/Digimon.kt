package com.cybersleuth.planner.database

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinTable
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.OrderBy

@Entity
class Digimon(
        @Id
        val id: Int,
        val name: String,
        val stage: String,
        val type: String,
        val attribute: String,
        val memory: Int,
        val slots: Int,
        @ManyToOne
        val skill: Skill,
        @OneToMany
        @JoinTable
        val evolveFrom: MutableSet<Digimon>,
        @OneToMany(mappedBy = "from", cascade = [CascadeType.ALL])
        @OrderBy("from")
        val evolveTo: MutableSet<Evolution>,
        @OneToMany(mappedBy = "learnedBy", cascade = [CascadeType.ALL])
        @OrderBy("learnedAttack")
        val learnedAttacks: MutableSet<LearnAttack>,
        @OneToMany(mappedBy = "digimon")
        val stats: MutableSet<DigimonStats>
) {


}