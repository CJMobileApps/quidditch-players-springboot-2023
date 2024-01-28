package com.cjmobileapps.quidditchplayers.data.model

import java.util.UUID

data class Player(
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val yearsPlayed: List<Int>,
    val favoriteSubject: String,
    val position: Int,
    val imageUrl: String,
    val house: HouseName
)
