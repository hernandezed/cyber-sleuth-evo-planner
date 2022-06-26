package com.cybersleuth.planner.database

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Skill(
        @Id
        val id: Int,
        val name: String,
        val description: String
) {
}