package com.cybersleuth.planner.business.usecase.impl

import com.cybersleuth.planner.business.bo.DigimonBo
import com.cybersleuth.planner.business.usecase.FindAllDigimonsUseCase
import com.cybersleuth.planner.database.Digimon
import com.cybersleuth.planner.database.repositories.DigimonRepository
import com.cybersleuth.planner.ports.DigimonPort
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component
import javax.transaction.Transactional

@Component
class FindAllDigimonsUseCaseImpl(val digimonPort: DigimonPort) : FindAllDigimonsUseCase {

    @Cacheable("digimons")
    @Transactional
    override fun execute(): Set<DigimonBo> = digimonPort.findAll()
}