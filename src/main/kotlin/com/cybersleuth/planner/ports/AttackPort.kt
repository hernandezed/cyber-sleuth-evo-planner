package com.cybersleuth.planner.ports

import com.cybersleuth.planner.business.bo.AttackBo
import com.cybersleuth.planner.database.Attack
import java.util.SortedSet

interface AttackPort {
    fun findByNames(name: Set<String>): Set<AttackBo>

    fun findByIds(ids: Set<Int>): Set<AttackBo>
}