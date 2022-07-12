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
        val hp: Int,
        val sp: Int,
        val attack: Int,
        val defense: Int,
        val intellect: Int,
        val speed: Int,
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
        val learnedAttacks: MutableSet<LearnAttack>
) {

    override fun toString(): String {
        return "Digimon(id=$id, name='$name', stage='$stage', type='$type', attribute='$attribute', memory=$memory, slots=$slots, hp=$hp, sp=$sp, attack=$attack, defense=$defense, intellect=$intellect, speed=$speed, skill=$skill, evolveFrom=$evolveFrom, evolveTo=$evolveTo, learnedAttacks=$learnedAttacks)"
    }
}