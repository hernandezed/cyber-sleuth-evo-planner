package com.cybersleuth.planner.api.dto.digimon.detail

class EvolutionDetailDto(id: Int, name: String, val requirement: RequirementDto) : MinimalDigimonDetailDto(id, name) {
}