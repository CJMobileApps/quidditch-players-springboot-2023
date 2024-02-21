package com.cjmobileapps.quidditchplayers.api.status.service

import com.cjmobileapps.quidditchplayers.util.ClientException
import com.cjmobileapps.quidditchplayers.util.InternalException
import java.util.*

interface StatusService {

    @Throws(ClientException::class, InternalException::class)
    suspend fun getStatusByHouseName(houseName: String?): String

    @Throws(ClientException::class, InternalException::class)
    suspend fun getStatusByPlayerId(playerId: UUID): String
}
