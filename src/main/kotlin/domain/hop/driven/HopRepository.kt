package domain.hop.driven

import domain.hop.model.Hop

interface HopRepository {
    fun getAll(): Map<String, Hop>
    fun save(hop: Hop)
    fun findByName(key: String): Hop?
    fun findSimilar(hop: Hop): Set<Hop>
}