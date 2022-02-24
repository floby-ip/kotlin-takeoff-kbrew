package domain.hop.model

import domain.country.model.Country
import domain.quantities.PercentRange
import domain.quantities.QuantityRange

data class Hop(
    val name: String,
    val country: List<Country>,
    val alpha: PercentRange,
    val beta: PercentRange,
    val coH: PercentRange,
    val oil: QuantityRange?,
    val type: List<HopType>,
    val profile: String,
    val similarTo: List<String>,
)

enum class HopType {
    AROMATIC,
    BITTERING;

    companion object {
        fun forStr(string: String) = when (string.uppercase()) {
            "AMÉRISANT" -> listOf(BITTERING)
            "AROMATIQUE" -> listOf(AROMATIC)
            "DOUBLE" -> listOf(AROMATIC, BITTERING)
            else -> error("Unknown type: $string")
        }
    }

}