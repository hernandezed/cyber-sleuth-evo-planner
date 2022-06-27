package com.cybersleuth.planner.business.usecase.impl

import com.cybersleuth.planner.business.usecase.FindAllDigimonsUseCase
import com.cybersleuth.planner.database.Digimon
import com.cybersleuth.planner.database.repositories.DigimonRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component
import javax.transaction.Transactional

@Component
class FindAllDigimonsUseCaseImpl(val digimonRepository: DigimonRepository) : FindAllDigimonsUseCase {

    @Cacheable("digimons")
    @Transactional
    override fun execute(): Set<Digimon> {
        return digimonRepository.findAll(Sort.by(Sort.Order.by("id"))).toSet()
    }
}