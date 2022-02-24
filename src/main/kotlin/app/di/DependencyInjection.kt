package app.di

import domain.hop.driven.HopRepository
import domain.malt.driven.MaltRepository
import domain.otheringredients.driven.OtherIngredientRepository
import domain.recipe.driven.RecipeRepository
import domain.yeast.driven.YeastRepository

object DependencyInjection {
    lateinit var hopRepository: HopRepository
    lateinit var yeastRepository: YeastRepository
    lateinit var maltRepository: MaltRepository
    lateinit var recipeRepository: RecipeRepository
    lateinit var otherIngredientRepository: OtherIngredientRepository
}