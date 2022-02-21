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
                "([0-9]*[.,]?[0-9]+) *[%]?[-]([0-9]*[.,]?[0-9]+) *[%]?".toRegex()
                    .matchEntire(string)?.destructured?.let { (first, second) ->
                        PercentRange(first.replace(",", ".").toDouble(), second.replace(",", ".").toDouble())
                    }
            } else {
                "([0-9]*,?[0-9]+) *[%]?".toRegex()
                    .matchEntire(string)?.destructured?.let { (first) ->
                        PercentRange(first.replace(",", ".").toDouble(), first.replace(",", ".").toDouble())
                    }
            } ?: error("unparsable range : $string")
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
                "([0-9]*[.,]?[0-9]+) *[-]([0-9]*[.,]?[0-9]+) *.*".toRegex()
                    .matchEntire(string)?.destructured?.let { (first, second) ->
                        QuantityRange(first.replace(",", ".").toDouble(), second.replace(",", ".").toDouble())
                    }
            } else {
                "([0-9]*,?[0-9]+) *.*".toRegex()
                    .matchEntire(string)?.destructured?.let { (first) ->
                        QuantityRange(first.replace(",", ".").toDouble(), first.replace(",", ".").toDouble())
                    }
            } ?: error("unparsable range : $string")
    }
}

