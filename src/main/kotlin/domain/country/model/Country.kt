package domain.country.model

enum class Country(val frName: String) {
    UK("ROYAUME-UNI"),
    FRANCE("FRANCE"),
    SLOVENIA("SLOVÉNIE"),
    GERMANY("ALLEMAGNE"),
    AUSTRALIA("AUSTRALIE"),
    NZ("NOUVELLE-ZÉLANDE"),
    POLAND("POLOGNE"),
    RUSSIA("RUSSIA"),
    JAPAN("JAPON"),
    CZECK("RÉPUBLIQUE-TCHÈQUE"),
    USA("ÉTATS-UNIS");

    companion object {
        fun fromFrName(string: String): List<Country> = string.uppercase().split(" OU ").map { upper ->
            values().firstOrNull { it.frName == upper } ?: error("unknown country : $upper")
        }
    }
}
