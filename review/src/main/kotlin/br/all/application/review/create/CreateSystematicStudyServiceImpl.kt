package br.all.application.review.create

import br.all.application.researcher.credentials.ResearcherCredentialsService
import br.all.application.review.create.CreateSystematicStudyService.RequestModel
import br.all.application.review.create.CreateSystematicStudyService.ResponseModel
import br.all.application.review.repository.SystematicStudyRepository
import br.all.application.review.repository.fromRequestModel
import br.all.application.review.repository.toDto
import br.all.application.shared.presenter.PreconditionChecker
import br.all.domain.model.researcher.ResearcherId
import br.all.domain.model.review.SystematicStudy
import br.all.domain.services.UuidGeneratorService
import java.util.*

class CreateSystematicStudyServiceImpl(
    private val repository: SystematicStudyRepository,
    private val uuidGeneratorService: UuidGeneratorService,
    private val credentialsService: ResearcherCredentialsService,
): CreateSystematicStudyService {
    override fun create(presenter: CreateSystematicStudyPresenter, researcher: UUID, request: RequestModel) {
        PreconditionChecker(repository, credentialsService).also {
            it.prepareIfUnauthenticatedOrUnauthorized(presenter, ResearcherId(researcher))
        }
        if (presenter.isDone()) return

        val id = uuidGeneratorService.next()
        SystematicStudy.fromRequestModel(id, researcher, request).also {
            repository.saveOrUpdate(it.toDto())
        }

        presenter.prepareSuccessView(ResponseModel(researcher, id))
    }
}
