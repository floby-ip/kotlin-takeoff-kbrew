package domain.quantities

data class PercentRange(
    val from: Double? = null,
    val to: Double? = null,
) {
    companion object {
        fun fromStr(string: String) =
            if (string.isBlank()) {
                PercentRange()
            } else if (string.contains("-")) {
                "([0-9]*[.,]?[0-9]+) *[%]? *[-] *([0-9]*[.,]?[0-9]+) *[%]?".toRegex()
                    .matchEntire(string)?.destructured?.let { (first, second) ->
                        PercentRange(first.replace(",", ".").toDouble(), second.replace(",", ".").toDouble())
                    }
            } else {
                "([0-9]*,?[0-9]+) *[%]?".toRegex()
                    .matchEntire(string)?.destructured?.let { (first) ->
                        PercentRange(first.replace(",", ".").toDouble(), first.replace(",", ".").toDouble())
                    }
            } ?: error("unparsable range : $string") // TODO refacto to either
    }
}

data class QuantityRange(
    val from: Double? = null,
    val to: Double? = null,
) {
    companion object {
        fun fromStr(string: String) =
            if (string.isBlank()) {
                QuantityRange()
            } else if (string.contains("-")) {
                "([0-9]*[.,]?[0-9]+) *[-] *([0-9]*[.,]?[0-9]+) *.*".toRegex()
                    .matchEntire(string)?.destructured?.let { (first, second) ->
                        QuantityRange(first.replace(",", ".").toDouble(), second.replace(",", ".").toDouble())
                    }
            } else {
                "([0-9]*,?[0-9]+) *.*".toRegex()
                    .matchEntire(string)?.destructured?.let { (first) ->
                        QuantityRange(first.replace(",", ".").toDouble(), first.replace(",", ".").toDouble())
                    }
            } ?: error("unparsable range : $string") // TODO refacto to either
    }
}

data class Percent(
    val value: Double? = null,
) {
    companion object {
        fun fromStr(string: String) =
            if (string.isBlank()) {
                Percent()
            } else {
                "([0-9]*[.,]?[0-9]+) *[%]?".toRegex()
                    .matchEntire(string)?.destructured?.let { (first) ->
                        Percent(first.replace(",", ".").toDouble())
                    }
            } ?: error("unparsable percent : $string")
    }
}

data class Quantity(
    val value: Double,
    val unit: QuantityUnit,
) {
    fun convert(to: QuantityUnit): Quantity = Quantity(
        value = value * Conversion.getRatio(unit, to),
        unit = to,
    )
}

enum class QuantityUnit(val symbol: String, val singular: String, val plural: String) {
    CL("cL", "centiliter", "centiliters"),
    ML("mL", "milliliter", "milliliters"),
    L("L", "liter", "liters"),
    G("g", "gram", "grams"),
    KG("kg", "kilogram", "kilograms"),
}

enum class Conversion(
    val from: QuantityUnit,
    val to: QuantityUnit,
    val ratio: Double,
) {
    CL_L(QuantityUnit.CL, QuantityUnit.L, 0.01),
    ML_L(QuantityUnit.ML, QuantityUnit.L, 0.001),
    ML_CL(QuantityUnit.ML, QuantityUnit.CL, 0.1),
    G_KG(QuantityUnit.G, QuantityUnit.KG, 0.001);

    companion object {
        fun getRatio(from: QuantityUnit, to: QuantityUnit): Double =
            values().firstOrNull { it.from == from && it.to == to }?.ratio
                ?: values().firstOrNull { it.from == to && it.to == from }?.let { conversion -> 1 / conversion.ratio }
                ?: throw IllegalArgumentException() // TODO refacto avec arrow (+TU)
    }

}
