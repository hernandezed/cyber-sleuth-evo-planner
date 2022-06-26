package com.cybersleuth.planner.database

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Attack(@Id
                  val id: Int,
                  val name: String,
                  var inheritable: Boolean?
)