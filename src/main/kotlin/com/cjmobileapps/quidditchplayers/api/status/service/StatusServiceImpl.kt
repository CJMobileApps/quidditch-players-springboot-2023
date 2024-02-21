package com.cjmobileapps.quidditchplayers.api.status.service

import com.cjmobileapps.quidditchplayers.api.status.repository.StatusDao
import com.cjmobileapps.quidditchplayers.util.ClientException
import com.cjmobileapps.quidditchplayers.util.InternalException
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class StatusServiceImpl(@Qualifier("statusRepository") val statusDao: StatusDao) : StatusService {

    @Throws(ClientException::class, InternalException::class)
    override suspend fun getStatusByHouseName(houseName: String?) = statusDao.getStatusByHouseName(houseName)

    @Throws(ClientException::class, InternalException::class)
    override suspend fun getStatusByPlayerId(playerId: UUID) = statusDao.getStatusByPlayerId(playerId)
}
