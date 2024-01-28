package com.cjmobileapps.quidditchplayers.api.player.service

import com.cjmobileapps.quidditchplayers.api.player.repository.PlayerDao
import com.cjmobileapps.quidditchplayers.data.model.Player
import com.cjmobileapps.quidditchplayers.util.ClientException
import com.cjmobileapps.quidditchplayers.util.InternalException
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class PlayerServiceImpl(@Qualifier("playerRepository") val playerDao: PlayerDao): PlayerService {

    @Throws(ClientException::class, InternalException::class)
    override suspend fun getPlayers(houseName: String?): List<Player> {
        return playerDao.getPlayers(houseName)
    }
}
