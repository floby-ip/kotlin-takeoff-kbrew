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
    description = "Malt produit avec de l'orge de printemps à 2 rangs de qualité. Parfait comme malt de base tant pour les lagers que pour les ales. Excellentes propriétés pour la filtration du moût. Donne une bière fine avec un corps et une longueur en bouche considérable. Il est aussi bon dans le développement de la mousse que pour sa tenue. Grain très polyvalent avec une grande efficacité d'extraction des sucres. Optimal dans tous les processus de fabrication, de l'infusion aux multi palliers en passant par la decoction. Saveur : malté-sucré et notes douces de miel.",
    bestFor = listOf(BeerType.PILS, BeerType.PALE_ALE, BeerType.NA_BEER, BeerType.LA_BEER),
)