package com.cjmobileapps.quidditchplayers.api.house.service

import com.cjmobileapps.quidditchplayers.api.house.repository.HouseDao
import com.cjmobileapps.quidditchplayers.data.model.House
import com.cjmobileapps.quidditchplayers.util.ClientException
import com.cjmobileapps.quidditchplayers.util.InternalException
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class HouseServiceImpl(@Qualifier("houseRepository") val houseDao: HouseDao): HouseService {

    @Throws(ClientException::class, InternalException::class)
    override suspend fun getAllHouses(): List<House> {
        return houseDao.getAllHouses()
    }
}
