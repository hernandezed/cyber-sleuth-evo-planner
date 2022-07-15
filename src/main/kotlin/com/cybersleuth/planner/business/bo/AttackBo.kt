package com.cybersleuth.planner.business.bo

data class AttackBo(val id: Int,
                    val name: String,
                    val attribute: String,
                    val type: String,
                    val cost: Int,
                    val power: Int,
                    val inheritable: Boolean) {
}