package infrastructure

import domain.hop.driven.HopRepository
import domain.hop.model.Hop

class InMemoryHopRepository : HopRepository {

    private var hops = emptyMap<String, Hop>()

    override fun getAll() = hops

    override fun save(hop: Hop) {
        hops = hops.plus(hop.name.uppercase().trim() to hop)
    }

    override fun findByName(key: String) = hops[key.uppercase().trim()]

    override fun findSimilar(hop: Hop): Set<Hop> {
        var res = emptySet<Hop>()
        if (hop.similarTo != null && !hop.similarTo.isEmpty()) {
            val names = hop.similarTo!!.split(",")
            for (s in names) {
                val similar = findByName(s)
                if (similar != null) {
                    res = res.plus(similar)
                }
            }
        }
        return res
    }

}