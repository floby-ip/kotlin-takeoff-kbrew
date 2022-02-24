package infrastructure

import domain.country.model.Country
import domain.hop.model.Hop
import domain.hop.model.HopType
import domain.quantities.PercentRange
import domain.quantities.QuantityRange
import fixtures.sampleHop
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.fail
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.maps.shouldHaveSize
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldNotBeBlank

class InMemoryHopRepositoryTest : ShouldSpec({

    val repoUnderTest = InMemoryHopRepository()

    beforeEach {
        repoUnderTest.clear()
    }

    should("find hop by name") {
        // Givent
        repoUnderTest.save(sampleHop)
        repoUnderTest.save(sampleHop.copy(name = "Cascade", similarTo = listOf("Citra")))
        repoUnderTest.save(sampleHop.copy(name = "Chinook", similarTo = listOf("Cascade", "Citra", "Chinook")))

        // When & Then
        repoUnderTest.findByName("Citra").shouldNotBeNull().apply {
            assertSoftly {
                name shouldBe "Citra"
                country.shouldContainExactlyInAnyOrder(Country.USA)
                alpha shouldBe PercentRange(10.0, 12.0)
                beta shouldBe PercentRange(3.5, 4.5)
                coH shouldBe PercentRange(22.0, 24.0)
                type.shouldContainExactlyInAnyOrder(HopType.AROMATIC, HopType.BITTERING)
                profile.shouldNotBeBlank()
                similarTo.shouldContainExactlyInAnyOrder("Cascade", "Centennial", "Chinook")
            }
        }
    }

    should("get all") {
        // Given
        repoUnderTest.save(sampleHop)
        repoUnderTest.save(sampleHop.copy(name = "Cascade", similarTo = listOf("Citra")))
        repoUnderTest.save(sampleHop.copy(name = "Chinook", similarTo = listOf("Cascade", "Citra", "Chinook")))

        // When
        val res = repoUnderTest.getAll()

        // Then
        res shouldHaveSize 3
        res["CASCADE"] shouldBe Hop(
            name = "Cascade",
            country = listOf(Country.USA),
            alpha = PercentRange(from = 10.0, to = 12.0),
            beta = PercentRange(from = 3.5, to = 4.5),
            coH = PercentRange(from = 22.0, to = 24.0),
            oil = QuantityRange(from = 1.5, to = 3.0),
            type = listOf(HopType.BITTERING, HopType.AROMATIC),
            profile = "Agrumes, pamplemousse, fruit de la passion",
            similarTo = listOf("Citra"),
        )
    }

    should("find all similar hops") {
        // Given
        repoUnderTest.save(sampleHop)
        repoUnderTest.save(sampleHop.copy(name = "Cascade", similarTo = listOf("Citra")))
        repoUnderTest.save(sampleHop.copy(name = "Centennial", similarTo = emptyList()))
        repoUnderTest.save(sampleHop.copy(name = "Chinook", similarTo = listOf("Cascade", "Citra", "Centennial")))
        val rootHop = repoUnderTest.findByName("Chinook") ?: fail("initialization error")

        // When & Then
        repoUnderTest.findSimilar(rootHop).shouldContainExactlyInAnyOrder(
            repoUnderTest.findByName("Cascade"),
            repoUnderTest.findByName("Centennial"),
            repoUnderTest.findByName("Citra"),
        )
    }
})