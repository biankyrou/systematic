package br.all.review.controller

import br.all.application.researcher.credentials.ResearcherCredentialsService
import br.all.application.review.create.CreateSystematicStudyServiceImpl
import br.all.application.review.find.services.FindAllSystematicStudiesServiceImpl
import br.all.application.review.find.services.FindOneSystematicStudyServiceImpl
import br.all.application.review.repository.SystematicStudyRepository
import br.all.domain.services.UuidGeneratorService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SystematicStudyServicesConfiguration {
    @Bean
    fun createSystematicStudyService(
        systematicStudyRepository: SystematicStudyRepository,
        uuidGeneratorService: UuidGeneratorService,
        credentialsService: ResearcherCredentialsService,
    ) = CreateSystematicStudyServiceImpl(systematicStudyRepository, uuidGeneratorService, credentialsService)

    @Bean
    fun findOneSystematicStudyService(
        systematicStudyRepository: SystematicStudyRepository,
        credentialsService: ResearcherCredentialsService,
    ) = FindOneSystematicStudyServiceImpl(systematicStudyRepository, credentialsService)

    @Bean
    fun findAllSystematicStudiesService(
        systematicStudyRepository: SystematicStudyRepository,
        credentialsService: ResearcherCredentialsService,
    ) = FindAllSystematicStudiesServiceImpl(systematicStudyRepository, credentialsService)
}
