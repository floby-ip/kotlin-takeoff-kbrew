package domain.otheringredients.driven

import domain.otheringredients.model.Ingredient

interface OtherIngredientRepository {
    fun clear(): Unit
    fun getAll(): Map<String, Ingredient>
    fun findByName(name: String): Ingredient?
    fun save(ingredient: Ingredient): Unit
}