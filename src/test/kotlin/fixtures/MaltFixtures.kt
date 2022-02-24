package fixtures

import domain.beertype.model.BeerType
import domain.malt.model.Malt
import domain.malt.model.MaltType
import domain.quantities.Percent

val sampleMalt = Malt(
    name = "Pilsner 2RP",
    type = MaltType.PILSNER,
    ebc = 3,
    recommendedMax = Percent(value = 100.0),
    description = "Short description",
    bestFor = listOf(BeerType.PILS, BeerType.PALE_ALE, BeerType.NA_BEER, BeerType.LA_BEER),
)