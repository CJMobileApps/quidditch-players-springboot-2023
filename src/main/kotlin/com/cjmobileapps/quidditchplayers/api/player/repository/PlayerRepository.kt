package com.cjmobileapps.quidditchplayers.api.player.repository

import com.cjmobileapps.quidditchplayers.data.MockData
import com.cjmobileapps.quidditchplayers.data.model.Player
import com.cjmobileapps.quidditchplayers.util.ClientException
import com.cjmobileapps.quidditchplayers.util.InternalException
import com.cjmobileapps.quidditchplayers.util.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Repository

@Repository("playerRepository")
class PlayerRepository() : PlayerDao {

    @Throws(ClientException::class, InternalException::class)
    override suspend fun getPlayers(houseName: String?): List<Player> {
        return coroutineScope {
            withContext(Dispatchers.Default) {
                try {
                    return@withContext if (houseName.isNullOrEmpty()) {
                        MockData.allQuidditchTeam
                    } else {
                        MockData
                            .allQuidditchTeam
                            .filter { player ->
                                player.house.name == houseName
                            }
                    }
                } catch (e: ClientException) {
                    Logger.errorStackTrace("getAllPlayers()", e)
                    throw ClientException(e.message)
                } catch (e: InternalException) {
                    Logger.errorStackTrace("getAllPlayers()", e)
                    throw InternalException()
                } catch (e: Exception) {
                    Logger.errorStackTrace("getAllPlayers()", e)
                    throw InternalException()
                }
            }
        }
    }
}
