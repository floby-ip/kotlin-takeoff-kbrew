package fixtures

import domain.country.model.Country
import domain.hop.model.Hop
import domain.hop.model.HopType
import domain.quantities.PercentRange
import domain.quantities.QuantityRange

val sampleHop = Hop(
    name = "Citra",
    country = listOf(Country.USA),
    alpha = PercentRange(from = 10.0, to = 12.0),
    beta = PercentRange(from = 3.5, to = 4.5),
    coH = PercentRange(from = 22.0, to = 24.0),
    oil = QuantityRange(from = 1.5, to = 3.0),
    type = listOf(HopType.BITTERING, HopType.AROMATIC),
    profile = "Agrumes, pamplemousse, fruit de la passion",
    similarTo = listOf("Cascade", "Centennial", "Chinook"),
)