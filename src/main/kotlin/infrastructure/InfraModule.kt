package infrastructure

import app.di.DependencyInjection

object InfraModule {
    fun initDi() {
        DependencyInjection.hopRepository = InMemoryHopRepository()
        DependencyInjection.yeastRepository = InMemoryYeastRepository()
        DependencyInjection.maltRepository = InMemoryMaltRepository()
        DependencyInjection.recipeRepository = InMemoryRecipeRepository()
    }
}
