package app.main

import infrastructure.InfraModule

fun main(args: Array<String>) {
    InfraModule.initDi()

    loadHopList()
}
