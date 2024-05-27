package com.cjmobileapps.quidditchplayers

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class QuidditchPlayersApplication

fun main(args: Array<String>) {
    runApplication<QuidditchPlayersApplication>(*args)
    serverDetails()
}

private fun serverDetails() {
    println("Server is up")
}
