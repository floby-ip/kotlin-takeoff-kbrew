package app.main

import app.di.DependencyInjection
import domain.country.model.Country
import domain.hop.model.Hop
import domain.hop.model.HopType
import domain.quantities.PercentRange
import domain.quantities.QuantityRange

const val hopListFile = "hop-list.txt"

fun loadHopList() =
    object {}.javaClass.getResource("/$hopListFile")?.readText()
        ?.plus("\n")
        ?.let { file ->
            var tmpHop = TmpHop()
            file.split("\n").forEachIndexed { index, line ->
                tmpHop = tmpHop.handleLine(index, line)
            }
        } ?: error("file should exist $hopListFile")

private data class TmpHop(
    val name: String? = null,
    val country: List<Country>? = null,
    val alpha: PercentRange? = null,
    val beta: PercentRange? = null,
    val coH: PercentRange? = null,
    val type: List<HopType>? = null,
    val oil: QuantityRange? = null,
    val profile: String? = null,
    val similarTo: String? = null,
) {
    fun toHop(index: Int) = Hop(
        name = name ?: error("name is required l.$index"),
        country = country ?: error("country is required for $name l.$index"),
        alpha = alpha ?: error("alpha is required for $name l.$index"),
        beta = beta ?: error("beta is required for $name l.$index"),
        coH = coH ?: error("coH is required for $name l.$index"),
        oil = oil,
        type = type ?: error("type is required for $name l.$index"),
        profile = profile ?: "",
        similarTo = similarTo?.split(",")?.map { it.trim() } ?: emptyList(),
    )

    fun handleLine(index: Int, line: String): TmpHop =
        when {
            line.startsWith("#") -> this
            line.isBlank() -> save(index)
            line.contains(":") -> addField(line)
            else -> addName(line)
        }

    private fun addName(line: String) = copy(name = line)

    private fun addField(line: String) = line.split(":").let { splitted ->
        val key = splitted[0].trim().lowercase()
        val value = splitted[1].trim()
        when (key) {
            "pays" -> copy(country = Country.fromFrName(value.uppercase()))
            "utilisation" -> copy(type = HopType.forStr(value.uppercase()))
            "alpha" -> copy(alpha = PercentRange.fromStr(value))
            "beta" -> copy(beta = PercentRange.fromStr(value))
            "co-h" -> copy(coH = PercentRange.fromStr(value))
            "huile" -> copy(oil = QuantityRange.fromStr(value))
            "profil" -> copy(profile = value)
            "similaire à" -> copy(similarTo = value)
            else -> error("unknown field: $key")
        }
    }

    private fun isEmpty() = name.isNullOrBlank()
            && country.isNullOrEmpty()
            && alpha == null
            && beta == null
            && coH == null
            && type.isNullOrEmpty()
            && oil == null
            && profile.isNullOrBlank()
            && similarTo.isNullOrBlank()

    private fun save(index: Int): TmpHop = this.takeUnless { isEmpty() }?.toHop(index)
        ?.let { DependencyInjection.hopRepository.save(it) }.run { TmpHop() }
}