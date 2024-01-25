package com.cjmobileapps.quidditchplayers.api.house.service

import com.cjmobileapps.quidditchplayers.data.model.House
import com.cjmobileapps.quidditchplayers.util.ClientException
import com.cjmobileapps.quidditchplayers.util.InternalException

interface HouseService {

    @Throws(ClientException::class, InternalException::class)
    suspend fun getAllHouses():  List<House>
}
