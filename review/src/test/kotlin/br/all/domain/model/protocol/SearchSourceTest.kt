package br.all.domain.model.protocol

import org.junit.jupiter.api.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@Tag("UnitTest")
class SearchSourceTest {
    @Nested
    @Tag("ValidClasses")
    @DisplayName("When the given source is valid")
    inner class WhenTheGivenSourceIsValid {
        @Test
        fun `should successfully create a valid search source`() {
            assertDoesNotThrow { SearchSource("Valid SearchSource") }
        }
    }

    @Nested
    @Tag("InvalidClasses")
    @DisplayName("When invalid sources are given")
    inner class WhenInvalidSourcesAreGiven {
        @Test
        fun `should throw if search source contains digits or symbols`() {
            assertThrows<IllegalArgumentException> { SearchSource("H& @ba O-O Dxd5 1m J0ta") }
        }

        @ParameterizedTest(name = "[{index}] source=\"{0}\"")
        @ValueSource(strings = ["", " ", "   "])
        fun `should throw to any kind of empty search source`(source: String) {
            assertThrows<IllegalArgumentException> { SearchSource(source) }
        }
    }
}
