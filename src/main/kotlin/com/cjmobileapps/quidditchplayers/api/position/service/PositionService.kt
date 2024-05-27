package com.cjmobileapps.quidditchplayers.api.position.service

import com.cjmobileapps.quidditchplayers.data.model.Position
import com.cjmobileapps.quidditchplayers.util.ClientException
import com.cjmobileapps.quidditchplayers.util.InternalException

interface PositionService {
    @Throws(ClientException::class, InternalException::class)
    suspend fun getPositions(): Map<Int, Position>
}
