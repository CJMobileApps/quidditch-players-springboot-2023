package com.cjmobileapps.quidditchplayers.api.status.repository

import com.cjmobileapps.quidditchplayers.util.ClientException
import com.cjmobileapps.quidditchplayers.util.InternalException
import java.util.*

interface StatusDao {

    @Throws(ClientException::class, InternalException::class)
    suspend fun getStatus(playerId: UUID): String
}
