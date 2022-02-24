package infrastructure

import domain.otheringredients.driven.OtherIngredientRepository
import domain.otheringredients.model.Ingredient

class InMemoryOtherIngredientRepository : OtherIngredientRepository {
    private var ingredients: Map<String, Ingredient> = emptyMap()

    override fun clear() {
        ingredients = emptyMap()
    }

    override fun getAll(): Map<String, Ingredient> = ingredients

    override fun findByName(name: String): Ingredient? = ingredients[name.uppercase().trim()]

    override fun save(ingredient: Ingredient) {
        ingredients = ingredients.plus(ingredient.name.uppercase() to ingredient)
    }
}