package com.cybersleuth.planner.domain

class Digimon(
        val id: Int,
        val portrait: String,
        val mini: String,
        val name: String,
        val level: String,
        val type: String,
        val memory: Int,
        val attribute: String,
        val slots: Int,
        val skill: Skill,
        val evolveFrom: List<String>,
        val evolveTo: List<Evolution>,
        val attacks: Set<LearnedAttack>
) {
}