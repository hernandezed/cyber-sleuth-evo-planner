package com.cybersleuth.planner.database

import javax.persistence.Embeddable

@Embeddable
data class Requirements(
        var level: Int? = null,
        var hp: Int? = null,
        var sp: Int? = null,
        var atk: Int? = null,
        var def: Int? = null,
        var int: Int? = null,
        var spd: Int? = null,
        var cam: String? = null,
        var abi: Int? = null,
        var dna: String? = null
) {
}