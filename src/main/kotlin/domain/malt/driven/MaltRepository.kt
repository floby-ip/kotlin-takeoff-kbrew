package domain.malt.driven

import domain.beertype.model.BeerType
import domain.malt.model.Malt

interface MaltRepository {
    fun getAll(): Map<String, Malt>
    fun save(malt: Malt)
    fun findByName(key: String): Malt?
    fun findBestForType(beerType: BeerType): Set<Malt>
    fun clear(): Unit
}