package br.all.application.search.create

import br.all.domain.model.protocol.SearchSource
import org.springframework.web.multipart.MultipartFile
import java.util.*

interface CreateSearchSessionService {
    fun createSession(presenter: CreateSearchSessionPresenter, request: RequestModel)

    data class RequestModel(
        val systematicStudy: UUID,
        val source: SearchSource,
        val searchString: String,
        val additionalInfo: String?,
        val bibFile: MultipartFile
    )

    open class ResponseModel(
        val sessionId: String,
        val message: String
    )
}