package com.cjmobileapps.quidditchplayers.data.model

import java.util.UUID

data class Status(
    val playerId: UUID,
    val status: String,
)
