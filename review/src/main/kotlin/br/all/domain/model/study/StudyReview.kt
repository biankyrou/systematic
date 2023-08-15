package br.all.domain.model.study

import br.all.domain.common.ddd.Entity
import br.all.domain.model.review.ReviewId
import java.util.*

class StudyReview(
    val id: StudyReviewId,
    val reviewId: ReviewId,
    val title: String,
    val year: Int,
    val authors: String,
    val venue: String,
    val abstract: String,
    val keywords: Set<String> = emptySet(),
    val references: List<String> = emptyList(),
    val doi: Doi? = null,
    val searchSources: MutableSet<String> = mutableSetOf(),
    val criteria: MutableSet<String> = mutableSetOf(),
    val formAnswers: MutableMap<UUID, Answer<*>> = mutableMapOf(),
    val qualityAnswers: MutableMap<UUID, Answer<*>> = mutableMapOf(),
    var comments: String = "",
    var readingPriority: ReadingPriority = ReadingPriority.LOW,
    selectionStatus: SelectionStatus = SelectionStatus.UNCLASSIFIED,
    extractionStatus: ExtractionStatus = ExtractionStatus.UNCLASSIFIED,
) : Entity(id) {

    private val study: Study
    var selectionStatus: SelectionStatus = selectionStatus
        private set
    var extractionStatus: ExtractionStatus = extractionStatus
        private set

    init {
        require(searchSources.size > 0) { "The study must be related to at least one search source." }
        study = Study(title, year, authors, venue, abstract, keywords, references, doi)
    }

    companion object {
        fun instance(
            id: StudyReviewId,
            reviewId: ReviewId,
            title: String,
            year: Int,
            authors: String,
            venue: String,
            abstract: String
        ) = StudyReview(id, reviewId, title, year, authors, venue, abstract)
    }

    fun addSearchSource(searchSource: String) = searchSources.add(searchSource)

    fun removeSearchSource(searchSource: String) =
        if (searchSource.length > 1) searchSources.remove(searchSource)
        else throw IllegalStateException("The study must be related to at least one search source.")

    fun addCriterion(criterion: String) = criteria.add(criterion)

    fun removeCriterion(criterion: String) = criteria.remove(criterion)

    fun answerQualityQuestionOf(questionId: UUID, answer: Answer<*>) = qualityAnswers.put(questionId, answer)

    fun answerFormQuestionOf(questionId: UUID, answer: Answer<*>) = formAnswers.put(questionId, answer)

    fun includeInSelection() = apply { selectionStatus = SelectionStatus.INCLUDED }

    fun excludeInSelection() {
        selectionStatus = SelectionStatus.EXCLUDED
        if (shouldNotConsiderForExtraction()) extractionStatus = ExtractionStatus.EXCLUDED
    }

    fun unclassifyInSelection() {
        selectionStatus = SelectionStatus.UNCLASSIFIED
        extractionStatus = ExtractionStatus.UNCLASSIFIED
    }

    private fun shouldNotConsiderForExtraction() =
        extractionStatus == ExtractionStatus.INCLUDED || extractionStatus == ExtractionStatus.UNCLASSIFIED

    fun includeInExtraction() {
        if (selectionStatus == SelectionStatus.EXCLUDED)
            throw IllegalStateException("A study excluded during selection can not be included during extraction.")
        if (selectionStatus == SelectionStatus.UNCLASSIFIED)
            selectionStatus = SelectionStatus.INCLUDED
        extractionStatus = ExtractionStatus.INCLUDED
    }

    fun excludeInExtraction() {
        if (selectionStatus == SelectionStatus.UNCLASSIFIED)
            selectionStatus = SelectionStatus.EXCLUDED
        extractionStatus = ExtractionStatus.EXCLUDED
    }

    fun unclassifyInExtraction() = apply { extractionStatus = ExtractionStatus.UNCLASSIFIED }

    override fun toString(): String {
        return "StudyReview(reviewId=$reviewId, searchSources=$searchSources, criteria=$criteria, " +
                "formAnswers=$formAnswers, qualityAnswers=$qualityAnswers, comments='$comments', " +
                "readingPriority=$readingPriority, extractionStatus=$extractionStatus, " +
                "selectionStatus=$selectionStatus, study=$study)"
    }

//    class Builder(
//        private val id: StudyReviewId,
//        private val reviewId: ReviewId,
//        private val title: String,
//        private val year: Int,
//        private val authors: String,
//        private val venue: String,
//        private val abstract: String,
//        private val keywords: Set<String>,
//        private val searchSources: MutableSet<String>
//    ) {
//        private var references: List<String> = emptyList()
//        private var doi: Doi? = null
//        private var criteria: MutableSet<String> = mutableSetOf()
//        private var formAnswers: MutableMap<UUID, Answer<*>> = mutableMapOf()
//        private var qualityAnswers: MutableMap<UUID, Answer<*>> = mutableMapOf()
//        private var comments: String = ""
//        private var readingPriority: ReadingPriority = ReadingPriority.LOW
//        private var extractionStatus: ExtractionStatus = ExtractionStatus.UNCLASSIFIED
//        private var selectionStatus: SelectionStatus = SelectionStatus.UNCLASSIFIED
//
//        companion object {
//            fun build(
//                id: StudyReviewId,
//                reviewId: ReviewId,
//                title: String,
//                year: Int,
//                authors: String,
//                venue: String,
//                abstract: String,
//                keywords: Set<String>,
//                searchSources: MutableSet<String>,
//                body: Builder.() -> Unit
//            ): StudyReview{
//                val builder = Builder(id, reviewId, title, year, authors, venue, abstract, keywords, searchSources)
//                builder.body()
//                return builder.build()
//            }
//        }
//
//        fun references(references: List<String>) = apply { this.references = references }
//        fun doi(doi: Doi?) = apply { this.doi = doi }
//        fun criteria(criteria: MutableSet<String>) = apply { this.criteria = criteria }
//        fun formAnswers(formAnswers: MutableMap<UUID, Answer<*>>) = apply { this.formAnswers = formAnswers }
//        fun qualityAnswers(qualityAnswers: MutableMap<UUID, Answer<*>>) = apply { this.qualityAnswers = qualityAnswers }
//        fun comments(comments: String) = apply { this.comments = comments }
//        fun readingPriority(readingPriority: ReadingPriority) = apply { this.readingPriority = readingPriority }
//        fun extractionStatus(extractionStatus: ExtractionStatus) = apply { this.extractionStatus = extractionStatus }
//        fun selectionStatus(selectionStatus: SelectionStatus) = apply { this.selectionStatus = selectionStatus }
//        fun build() = StudyReview(id, reviewId, title, year, authors, venue, abstract, keywords, references,
//            doi, searchSources, criteria, formAnswers, qualityAnswers, comments, readingPriority, extractionStatus,
//            selectionStatus)
//    }


}