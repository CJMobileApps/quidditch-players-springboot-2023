package com.cjmobileapps.quidditchplayers.api.position.service

import com.cjmobileapps.quidditchplayers.api.position.repository.PositionDao
import com.cjmobileapps.quidditchplayers.data.model.Position
import com.cjmobileapps.quidditchplayers.util.ClientException
import com.cjmobileapps.quidditchplayers.util.InternalException
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class PositionServiceImpl(@Qualifier("positionRepository") val positionDao: PositionDao): PositionService {

    @Throws(ClientException::class, InternalException::class)
    override suspend fun getPositions(): Map<Int, Position> {
        return positionDao.getPositions()
    }
}
