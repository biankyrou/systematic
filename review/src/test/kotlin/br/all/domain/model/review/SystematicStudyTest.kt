package br.all.domain.model.review

import br.all.domain.model.researcher.ResearcherId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.util.*

class SystematicStudyTest {
    @ParameterizedTest
    @CsvSource("'',Some description", "Some title,''")
    fun `Should not create systematic study without title or description`(title: String, description: String){
        assertThrows<IllegalArgumentException> {
            SystematicStudy(ReviewId(UUID.randomUUID()), title, description, ResearcherId(UUID.randomUUID()))
        }
    }

    @Test
    fun `Should owner be a collaborator`(){
        val ownerId = ResearcherId(UUID.randomUUID())
        val sut = SystematicStudy(ReviewId(UUID.randomUUID()), "title", "description", ownerId)
        assertTrue(sut.containsCollaborator(ownerId))
    }

    @Test
    fun `Should not allow removing owner from collaborators`(){
        val ownerId = ResearcherId(UUID.randomUUID())
        val sut = SystematicStudy(ReviewId(UUID.randomUUID()), "title", "description", ownerId)
        assertThrows<IllegalStateException> {  sut.removeCollaborator(ownerId) }
    }

    @Test
    fun `Should remove valid collaborator`(){
        val sut = SystematicStudy(ReviewId(UUID.randomUUID()), "title", "description",
                ResearcherId(UUID.randomUUID()))
        val researcherId = ResearcherId(UUID.randomUUID())

        sut.addCollaborator(researcherId)
        assertTrue(sut.containsCollaborator(researcherId))

        sut.removeCollaborator(researcherId)
        assertFalse(sut.containsCollaborator(researcherId))
    }

    @Test
    fun `Should throw if try to remove absent collaborator`(){
        val sut = SystematicStudy(ReviewId(UUID.randomUUID()), "title", "description",
                ResearcherId(UUID.randomUUID()))
        val newOwner = ResearcherId(UUID.randomUUID())
        assertThrows<NoSuchElementException> { sut.removeCollaborator(newOwner) }
    }

    @Test
    fun `Should add new collaborator`(){
        val sut = SystematicStudy(ReviewId(UUID.randomUUID()), "title", "description",
                ResearcherId(UUID.randomUUID()))
        sut.addCollaborator(ResearcherId(UUID.randomUUID()))
        assertEquals(2, sut.collaborators.size)
    }

    @Test
    fun `Should add new owner to collaborators if not present yet`(){
        val sut = SystematicStudy(ReviewId(UUID.randomUUID()), "title", "description",
                ResearcherId(UUID.randomUUID()))
        val newOwner = ResearcherId(UUID.randomUUID())

        sut.changeOwner(newOwner)

        assertAll("change owner",
            { assertTrue(sut.containsCollaborator(newOwner)) },
            { assertEquals(sut.owner, newOwner)}
        )
    }
}