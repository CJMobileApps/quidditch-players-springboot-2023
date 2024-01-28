package com.cjmobileapps.quidditchplayers.api.player.repository

import com.cjmobileapps.quidditchplayers.data.model.Player
import com.cjmobileapps.quidditchplayers.util.ClientException
import com.cjmobileapps.quidditchplayers.util.InternalException


interface PlayerDao {

    @Throws(ClientException::class, InternalException::class)
    suspend fun getPlayers(houseName: String?): List<Player>
}
