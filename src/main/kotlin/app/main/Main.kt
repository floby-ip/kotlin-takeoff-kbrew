package app.main

import app.di.DependencyInjection
import infrastructure.InfraModule

fun main(args: Array<String>) {
    InfraModule.initDi()

    DataInitializer.hopRepository = DependencyInjection.hopRepository
    loadHopList()
}
