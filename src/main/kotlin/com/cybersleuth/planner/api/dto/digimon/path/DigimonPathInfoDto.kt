package com.cybersleuth.planner.api.dto.digimon.path

import org.springframework.hateoas.RepresentationModel

open class DigimonPathInfoDto(val id: Int, val name: String) :
        RepresentationModel<DigimonPathInfoDto>() {
}