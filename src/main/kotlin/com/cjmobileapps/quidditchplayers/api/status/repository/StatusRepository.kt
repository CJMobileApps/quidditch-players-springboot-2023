package com.cjmobileapps.quidditchplayers.api.status.repository

import com.cjmobileapps.quidditchplayers.data.MockData
import com.cjmobileapps.quidditchplayers.util.ClientException
import com.cjmobileapps.quidditchplayers.util.InternalException
import com.cjmobileapps.quidditchplayers.util.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Repository
import java.util.*

@Repository("statusRepository")
class StatusRepository() : StatusDao {

    @Throws(ClientException::class, InternalException::class)
    override suspend fun getStatus(playerId: UUID): String {
        return coroutineScope {
            withContext(Dispatchers.Default) {
                try {
                    val player = MockData
                        .allQuidditchTeam
                        .first { player ->
                            player.id == playerId
                        }

                    MockData.getStatuses(
                        name = "${player.firstName} ${player.lastName}",
                        favoriteSubject = player.favoriteSubject
                    ).random()
                } catch (e: ClientException) {
                    Logger.errorStackTrace("getStatus()", e)
                    throw ClientException(e.message)
                } catch (e: InternalException) {
                    Logger.errorStackTrace("getStatus()", e)
                    throw InternalException()
                } catch (e: Exception) {
                    Logger.errorStackTrace("getStatus()", e)
                    throw InternalException()
                }
            }
        }
    }
}
