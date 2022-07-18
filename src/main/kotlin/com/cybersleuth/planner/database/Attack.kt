package com.cybersleuth.planner.database

import org.springframework.expression.spel.ast.OperatorPower
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OrderColumn

@Entity
data class Attack(@Id
                  val id: Int,
                  val name: String,
                  val attribute: String,
                  val type: String,
                  val cost: Int,
                  val power: Int,
                  var inheritable: Boolean
)

/*
    NAME        CHARACTER VARYING(255),
    ATTRIBUTE CHARACTER VARYING (255),
    TYPE CHARACTER VARYING (255),
    COST INTEGER ,
    POWER INTEGER ,
    INHERITABLE BIT
 */