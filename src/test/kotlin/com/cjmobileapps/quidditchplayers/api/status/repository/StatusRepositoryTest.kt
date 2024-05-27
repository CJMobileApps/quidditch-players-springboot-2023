package com.cjmobileapps.quidditchplayers.api.status.repository

import com.cjmobileapps.quidditchplayers.data.model.HouseName
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.UUID

class StatusRepositoryTest {
    private lateinit var statusRepository: StatusRepository

    @BeforeEach
    private fun setup() {
        statusRepository = StatusRepository()
    }

    @Test
    fun `getStatusByPlayerId success`(): Unit =
        runBlocking {
            // then
            val status = statusRepository.getStatusByPlayerId(UUID.fromString("fd1f2deb-9637-4214-b991-a1b8daf18a7b"))

            Assertions.assertEquals(
                UUID.fromString("fd1f2deb-9637-4214-b991-a1b8daf18a7b"),
                status.playerId,
            )
        }

    @Test
    fun `getStatusByHouseName success`(): Unit =
        runBlocking {
            // then
            val status = statusRepository.getStatusByHouseName(HouseName.GRYFFINDOR.name)

            Assertions.assertNotNull(status)
        }
}
