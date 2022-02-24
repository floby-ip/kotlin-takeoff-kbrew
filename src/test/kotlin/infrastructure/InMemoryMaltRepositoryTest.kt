package infrastructure

import domain.beertype.model.BeerType
import domain.malt.model.Malt
import domain.malt.model.MaltType
import domain.quantities.Percent
import fixtures.sampleMalt
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.maps.shouldHaveKeys
import io.kotest.matchers.maps.shouldHaveSize
import io.kotest.matchers.shouldBe

class InMemoryMaltRepositoryTest : ShouldSpec({
    val repoUnderTest = InMemoryMaltRepository()

    beforeEach {
        repoUnderTest.clear()
    }

    should("find malt by name") {
        // Given
        repoUnderTest.save(sampleMalt)
        repoUnderTest.save(
            sampleMalt.copy(
                name = "Munich II",
                type = MaltType.MUNICH,
                ebc = 25,
                bestFor = listOf(BeerType.DUBBEL, BeerType.BOCK),
            ),
        )
        repoUnderTest.save(
            sampleMalt.copy(
                name = "Marris Otter",
                type = MaltType.MARRIS_OTTER,
                ebc = 6,
                bestFor = listOf(BeerType.ALE, BeerType.PALE_ALE, BeerType.IPA, BeerType.PORTER, BeerType.STOUT),
            ),
        )

        // When & Then
        repoUnderTest.findByName("Munich II") shouldBe Malt(
            name = "Munich II",
            type = MaltType.MUNICH,
            ebc = 25,
            recommendedMax = Percent(value = 100.0),
            description = "Short description",
            bestFor = listOf(BeerType.DUBBEL, BeerType.BOCK),
        )
    }

    should("get all") {
        // Given
        repoUnderTest.save(sampleMalt)
        repoUnderTest.save(
            sampleMalt.copy(
                name = "Munich II",
                type = MaltType.MUNICH,
                ebc = 25,
                bestFor = listOf(BeerType.DUBBEL, BeerType.BOCK),
            ),
        )
        repoUnderTest.save(
            sampleMalt.copy(
                name = "Marris Otter",
                type = MaltType.MARRIS_OTTER,
                ebc = 6,
                bestFor = listOf(BeerType.ALE, BeerType.PALE_ALE, BeerType.IPA, BeerType.PORTER, BeerType.STOUT),
            ),
        )

        // When
        val result = repoUnderTest.getAll()

        // Then
        result shouldHaveSize 3
        result.shouldHaveKeys("PILSNER 2RP", "MUNICH II", "MARRIS OTTER")
    }

    should("find from beer type") {
        // Given
        repoUnderTest.save(sampleMalt)
        repoUnderTest.save(
            sampleMalt.copy(
                name = "Munich II",
                type = MaltType.MUNICH,
                ebc = 25,
                bestFor = listOf(BeerType.DUBBEL, BeerType.BOCK),
            ),
        )
        repoUnderTest.save(
            sampleMalt.copy(
                name = "Marris Otter",
                type = MaltType.MARRIS_OTTER,
                ebc = 6,
                bestFor = listOf(BeerType.ALE, BeerType.PALE_ALE, BeerType.IPA, BeerType.PORTER, BeerType.STOUT),
            ),
        )

        // When & Then
        repoUnderTest.findBestForType(BeerType.BOCK).shouldHaveSize(1).toList()[0].name.shouldBe("Munich II")
    }
})