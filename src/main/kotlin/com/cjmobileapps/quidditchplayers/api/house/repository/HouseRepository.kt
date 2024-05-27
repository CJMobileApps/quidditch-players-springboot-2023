package com.cjmobileapps.quidditchplayers.api.house.repository

import com.cjmobileapps.quidditchplayers.data.MockData
import com.cjmobileapps.quidditchplayers.data.model.House
import com.cjmobileapps.quidditchplayers.util.ClientException
import com.cjmobileapps.quidditchplayers.util.InternalException
import com.cjmobileapps.quidditchplayers.util.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Repository

@Repository("houseRepository")
class HouseRepository() : HouseDao {
    @Throws(ClientException::class, InternalException::class)
    override suspend fun getAllHouses(): List<House> {
        return coroutineScope {
            withContext(Dispatchers.Default) {
                try {
                    MockData.houses
                } catch (e: ClientException) {
                    Logger.errorStackTrace("getAllHouses()", e)
                    throw ClientException(e.message)
                } catch (e: InternalException) {
                    Logger.errorStackTrace("getAllHouses()", e)
                    throw InternalException()
                } catch (e: Exception) {
                    Logger.errorStackTrace("getAllHouses()", e)
                    throw InternalException()
                }
            }
        }
    }
}
