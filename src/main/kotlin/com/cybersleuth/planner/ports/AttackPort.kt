package com.cybersleuth.planner.ports

import com.cybersleuth.planner.business.bo.AttackBo

interface AttackPort {
    fun findByNames(name: Set<String>): Set<AttackBo>
    fun findById(id: Int): AttackBo
    fun findByIds(ids: Set<Int>): Set<AttackBo>
}