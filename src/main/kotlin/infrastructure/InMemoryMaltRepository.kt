package infrastructure

import domain.beertype.model.BeerType
import domain.malt.driven.MaltRepository
import domain.malt.model.Malt

class InMemoryMaltRepository : MaltRepository {
    private var malts = emptyMap<String, Malt>()

    override fun getAll() = malts

    override fun save(malt: Malt) {
        malts = malts.plus(malt.name.uppercase().trim() to malt)
    }

    override fun findByName(key: String) = malts[key.uppercase().trim()]

    override fun findBestForType(beerType: BeerType): Set<Malt> =
        malts.values.filter { it.bestFor.contains(beerType) }.toSet()

    override fun clear() {
        malts = emptyMap()
    }

}