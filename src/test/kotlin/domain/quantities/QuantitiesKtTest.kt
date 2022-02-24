package domain.quantities

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class QuantitiesKtTest : ShouldSpec({
    context("PercentRange") {
        listOf(
            "3" to PercentRange(3.0, 3.0),
            "3%" to PercentRange(3.0, 3.0),
            "3,5-7" to PercentRange(3.5, 7.0),
            "3.5-7,2" to PercentRange(3.5, 7.2),
            "3,5-7.2%" to PercentRange(3.5, 7.2),
            "3.5%-7.2%" to PercentRange(3.5, 7.2),
            "3.5 - 7.2%" to PercentRange(3.5, 7.2),
            "3.5 - 7.2 %" to PercentRange(3.5, 7.2),
            "3.5 % - 7.2 %" to PercentRange(3.5, 7.2),
        ).forEach { (input, expected) ->
            should("""convert "$input" to $expected""") {
                PercentRange.fromStr(input) shouldBe expected
            }
        }
    }

    context("QuantityRange") {
        listOf(
            "3" to QuantityRange(3.0, 3.0),
            "3-5" to QuantityRange(3.0, 5.0),
            "3,5-7" to QuantityRange(3.5, 7.0),
            "3.5-7,2" to QuantityRange(3.5, 7.2),
            "3,5 -7.2" to QuantityRange(3.5, 7.2),
            "3.5-7.2text" to QuantityRange(3.5, 7.2),
            "3.5 - 7.2text" to QuantityRange(3.5, 7.2),
            "3.5 - 7.2 text" to QuantityRange(3.5, 7.2),
        ).forEach { (input, expected) ->
            should("""convert "$input" to $expected""") {
                QuantityRange.fromStr(input) shouldBe expected
            }
        }
    }

    context("Percent") {
        listOf(
            "3%" to Percent(3.0),
            "3 %" to Percent(3.0),
            "3,5%" to Percent(3.5),
            "3.5%" to Percent(3.5),
            "3,5 %" to Percent(3.5),
            "3.5 %" to Percent(3.5),
        ).forEach { (input, expected) ->
            should("""convert "$input" to $expected""") {
                Percent.fromStr(input) shouldBe expected
            }
        }
    }

    context("Quantity") {
        should("convert from L to CL") {
            Quantity(2.5, QuantityUnit.L).convert(QuantityUnit.CL) shouldBe Quantity(250.0, QuantityUnit.CL)
        }
    }

    context("Conversion") {
        listOf(
            Triple(QuantityUnit.CL, QuantityUnit.L, 0.01),
            Triple(QuantityUnit.ML, QuantityUnit.L, 0.001),
            Triple(QuantityUnit.ML, QuantityUnit.CL, 0.1),
            Triple(QuantityUnit.G, QuantityUnit.KG, 0.001),
            Triple(QuantityUnit.L, QuantityUnit.CL, 100),
            Triple(QuantityUnit.L, QuantityUnit.ML, 1000),
            Triple(QuantityUnit.CL, QuantityUnit.ML, 10),
            Triple(QuantityUnit.KG, QuantityUnit.G, 1000),
        ).forEach { (from, to, ratio) ->
            should("return ratio $ratio for converting $to to $from") {
                Conversion.getRatio(from, to) shouldBe ratio
            }
        }
    }
})