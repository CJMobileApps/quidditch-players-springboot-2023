package com.cjmobileapps.quidditchplayers.api.position.repository

import com.cjmobileapps.quidditchplayers.data.model.Position
import com.cjmobileapps.quidditchplayers.util.ClientException
import com.cjmobileapps.quidditchplayers.util.InternalException

interface PositionDao {
    @Throws(ClientException::class, InternalException::class)
    suspend fun getPositions(): Map<Int, Position>
}
