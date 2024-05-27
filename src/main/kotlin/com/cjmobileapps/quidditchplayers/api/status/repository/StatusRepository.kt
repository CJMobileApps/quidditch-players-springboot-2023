package com.cjmobileapps.quidditchplayers.api.status.repository

import com.cjmobileapps.quidditchplayers.data.MockData
import com.cjmobileapps.quidditchplayers.data.model.Status
import com.cjmobileapps.quidditchplayers.util.ClientException
import com.cjmobileapps.quidditchplayers.util.InternalException
import com.cjmobileapps.quidditchplayers.util.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository("statusRepository")
class StatusRepository : StatusDao {
    @Throws(ClientException::class, InternalException::class)
    override suspend fun getStatusByHouseName(houseName: String?): Status {
        return coroutineScope {
            withContext(Dispatchers.Default) {
                try {
                    val players =
                        if (!houseName.isNullOrEmpty()) {
                            MockData.allQuidditchTeam.filter {
                                it.house.name == houseName
                            }
                        } else {
                            MockData.allQuidditchTeam
                        }

                    val player = players.random()
                    val status =
                        MockData.getStatuses(
                            name = "${player.firstName} ${player.lastName}",
                            favoriteSubject = player.favoriteSubject,
                        ).random()

                    Status(
                        playerId = player.id,
                        status = status,
                    )
                } catch (e: ClientException) {
                    Logger.errorStackTrace("getStatusByHouseName()", e)
                    throw ClientException(e.message)
                } catch (e: InternalException) {
                    Logger.errorStackTrace("getStatusByHouseName()", e)
                    throw InternalException()
                } catch (e: Exception) {
                    Logger.errorStackTrace("getStatusByHouseName()", e)
                    throw InternalException()
                }
            }
        }
    }

    @Throws(ClientException::class, InternalException::class)
    override suspend fun getStatusByPlayerId(playerId: UUID): Status {
        return coroutineScope {
            withContext(Dispatchers.Default) {
                try {
                    val player =
                        MockData
                            .allQuidditchTeam
                            .first { player ->
                                player.id == playerId
                            }

                    val status =
                        MockData.getStatuses(
                            name = "${player.firstName} ${player.lastName}",
                            favoriteSubject = player.favoriteSubject,
                        ).random()

                    Status(
                        playerId = player.id,
                        status = status,
                    )
                } catch (e: ClientException) {
                    Logger.errorStackTrace("getStatusByPlayerId()", e)
                    throw ClientException(e.message)
                } catch (e: InternalException) {
                    Logger.errorStackTrace("getStatusByPlayerId()", e)
                    throw InternalException()
                } catch (e: Exception) {
                    Logger.errorStackTrace("getStatusByPlayerId()", e)
                    throw InternalException()
                }
            }
        }
    }
}
