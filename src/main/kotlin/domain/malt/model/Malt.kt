package domain.malt.model

import domain.beertype.model.BeerType
import domain.quantities.Percent

data class Malt(
    val name: String,
    val type: MaltType,
    val ebc: Int,
    val recommendedMax: Percent,
    val description: String,
    val bestFor: List<BeerType>,
)

enum class MaltType {
    PILSNER,
    MARRIS_OTTER,
    MUNICH,
}