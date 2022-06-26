package com.cybersleuth.planner.database

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinTable
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity
class Digimon(
        @Id
        val id: Int,
        val name: String,
        val portrait: String,
        val mini: String,
        val stage: String,
        val type: String,
        val memory: Int,
        val slots: Int,
        @ManyToOne
        val skill: Skill,
        @OneToMany
        @JoinTable
        val evolveFrom: MutableSet<Digimon>,
        @OneToMany(mappedBy = "id", cascade = [CascadeType.ALL])
        val evolveTo: MutableSet<Evolution>,
        @OneToMany(mappedBy = "learnedBy", cascade = [CascadeType.ALL])
        val learnedAttacks: MutableSet<LearnAttack>
) {

    override fun toString(): String {
        return "Digimon(id=$id, name='$name', portrait='$portrait', mini='$mini', stage='$stage', type='$type', memory=$memory, slots=$slots, skill=$skill, evolveFrom=${evolveFrom.map { it.id }}, evolveTo=${evolveTo.map { it.to.id }})"
    }
}