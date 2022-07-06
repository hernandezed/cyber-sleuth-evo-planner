package com.cybersleuth.planner.api.dto.detail

class EvolutionDetailDto(id: Int, name: String, mini: String, val requirement: RequirementDto) : MinimalDigimonDetailDto(id, name, mini) {
}