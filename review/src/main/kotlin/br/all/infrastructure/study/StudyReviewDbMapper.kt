package br.all.infrastructure.study

import br.all.application.study.repository.StudyReviewDto

fun StudyReviewDocument.toDto() = StudyReviewDto(
    id,
    reviewId,
    type,
    title,
    year,
    authors,
    venue,
    abstractText,
    keywords,
    references,
    doi,
    searchSources,
    criteria,
    formAnswers,
    qualityAnswers,
    comments,
    readingPriority,
    extractionStatus,
    selectionStatus
)

fun StudyReviewDto.toDocument() = StudyReviewDocument(
    id,
    reviewId,
    studyType,
    title,
    year,
    authors,
    venue,
    abstract,
    keywords,
    references,
    doi,
    searchSources,
    criteria,
    formAnswers,
    qualityAnswers,
    comments,
    readingPriority,
    extractionStatus,
    selectionStatus
)


