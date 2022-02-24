package infrastructure

import domain.beertype.model.BeerType
import fixtures.sampleMalt
import io.kotest.assertions.fail
import io.kotest.core.spec.style.ShouldSpec

class InMemoryMaltRepositoryTest : ShouldSpec({
    val repoUnderTest = InMemoryMaltRepository()

    beforeEach {
        repoUnderTest.clear()
    }

    should("find malt by name") {
        // Givent
        repoUnderTest.save(sampleMalt)
        repoUnderTest.save(
            sampleMalt.copy(
                name = "Munich II",
                ebc = 25,
                bestFor = listOf(BeerType.DUBBEL, BeerType.BOCK),
            ),
        )
        repoUnderTest.save(
            sampleMalt.copy(
                name = "Marris Otter",
                ebc = 6,
                bestFor = listOf(BeerType.ALE, BeerType.PALE_ALE, BeerType.IPA, BeerType.PORTER, BeerType.STOUT),
            ),
        )

        // When & Then
        fail("TODO")
    }

    should("get all") {
        fail("TODO")
    }

    should("find all similar hops") {
        fail("TODO")
    }
})