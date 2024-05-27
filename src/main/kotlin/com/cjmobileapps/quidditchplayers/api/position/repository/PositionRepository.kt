package com.cjmobileapps.quidditchplayers.api.position.repository

import com.cjmobileapps.quidditchplayers.data.MockData
import com.cjmobileapps.quidditchplayers.data.model.Position
import com.cjmobileapps.quidditchplayers.util.ClientException
import com.cjmobileapps.quidditchplayers.util.InternalException
import com.cjmobileapps.quidditchplayers.util.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Repository

@Repository("positionRepository")
class PositionRepository() : PositionDao {
    @Throws(ClientException::class, InternalException::class)
    override suspend fun getPositions(): Map<Int, Position> {
        return coroutineScope {
            withContext(Dispatchers.Default) {
                try {
                    MockData.positions
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
