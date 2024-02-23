package com.cjmobileapps.quidditchplayers.api.status.repository

import com.cjmobileapps.quidditchplayers.data.model.Status
import com.cjmobileapps.quidditchplayers.util.ClientException
import com.cjmobileapps.quidditchplayers.util.InternalException
import java.util.*

interface StatusDao {

    @Throws(ClientException::class, InternalException::class)
    suspend fun getStatusByHouseName(houseName: String?): Status

    @Throws(ClientException::class, InternalException::class)
    suspend fun getStatusByPlayerId(playerId: UUID): Status
}
