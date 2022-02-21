package infrastructure

import app.main.DataInitializer
import app.main.loadHopList
import domain.country.model.Country
import domain.hop.model.Hop
import domain.hop.model.HopType
import domain.quantities.PercentRange
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.fail
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.maps.shouldHaveSize
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContainInOrder
import io.kotest.matchers.string.shouldNotBeBlank

class InMemoryHopRepositoryTest : ShouldSpec({

    val repoUnderTest = InMemoryHopRepository()

    beforeSpec {
        DataInitializer.hopRepository = repoUnderTest
        loadHopList()
    }

    should("find hop by name") {
        repoUnderTest.findByName("Citra").shouldNotBeNull().apply {
            assertSoftly {
                name shouldBe "Citra"
                country.shouldContainExactlyInAnyOrder(Country.USA)
                alpha shouldBe PercentRange(10.0, 12.0)
                beta shouldBe PercentRange(3.5, 4.5)
                coH shouldBe PercentRange(22.0, 24.0)
                type.shouldContainExactlyInAnyOrder(HopType.AROMATIC, HopType.BITTERING)
                profile.shouldNotBeBlank()
                similarTo.shouldContainInOrder("Galaxy", "Centennial", "Nelson Sauvin")
            }
        }
    }

    should("get all") {
        val res = repoUnderTest.getAll()
        res shouldHaveSize 114
        res["CASCADE"] shouldBe Hop(
            name = "Cascade",
            country = listOf(Country.USA),
            alpha = PercentRange(from = 7.7, to = 9.1),
            beta = PercentRange(from = 6.4, to = 7.1),
            coH = PercentRange(from = 33.0, to = 40.0),
            type = listOf(HopType.AROMATIC),
            profile = "Légèrement floral avec des notes très agrumes (pamplemousse)",
            similarTo = "Centennial"
        )
    }

    should("find all similar hops") {
        val rootHop = repoUnderTest.findByName("Zythos") ?: fail("initialization error")

        repoUnderTest.findSimilar(rootHop).shouldContainExactlyInAnyOrder(
            repoUnderTest.findByName("Cascade"),
            repoUnderTest.findByName("Ahtanum"),
            repoUnderTest.findByName("Sorachi Ace"),
        )
    }
})