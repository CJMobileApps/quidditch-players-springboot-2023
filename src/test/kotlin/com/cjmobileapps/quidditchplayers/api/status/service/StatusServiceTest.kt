package com.cjmobileapps.quidditchplayers.api.status.service

import com.cjmobileapps.quidditchplayers.api.player.repository.PlayerDao
import com.cjmobileapps.quidditchplayers.api.player.service.PlayerService
import com.cjmobileapps.quidditchplayers.api.player.service.PlayerServiceImpl
import com.cjmobileapps.quidditchplayers.api.status.repository.StatusDao
import com.cjmobileapps.quidditchplayers.data.MockData
import com.cjmobileapps.quidditchplayers.data.model.HouseName
import com.cjmobileapps.quidditchplayers.data.model.Status
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.*

class StatusServiceTest {

    @Mock
    lateinit var mockStatusDao: StatusDao

    lateinit var statusService: StatusService

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        statusService = StatusServiceImpl(mockStatusDao)
    }

    @Test
    fun `getStatusByPlayerId success`(): Unit = runBlocking {

        // given
        val mockStatus = Status(
            playerId = UUID.fromString("fd1f2deb-9637-4214-b991-a1b8daf18a7b"),
            status = MockData.getStatuses(name = "Harry Potter", "Defense Against The Dark Arts").first()
        )

        // when
        Mockito.`when`(mockStatusDao.getStatusByPlayerId(UUID.fromString("fd1f2deb-9637-4214-b991-a1b8daf18a7b")))
            .thenReturn(mockStatus)

        // then
        val status = statusService.getStatusByPlayerId(UUID.fromString("fd1f2deb-9637-4214-b991-a1b8daf18a7b"))

        Assertions.assertEquals(
            mockStatus,
            status
        )
    }

    @Test
    fun `getStatusByHouseName success`(): Unit = runBlocking {

        // given
        val mockStatus = Status(
            playerId = UUID.fromString("fd1f2deb-9637-4214-b991-a1b8daf18a7b"),
            status = MockData.getStatuses(name = "Harry Potter", "Defense Against The Dark Arts").first()
        )

        // when
        Mockito.`when`(mockStatusDao.getStatusByHouseName(HouseName.GRYFFINDOR.name))
            .thenReturn(mockStatus)

        // then
        val status = statusService.getStatusByHouseName(HouseName.GRYFFINDOR.name)

        Assertions.assertEquals(
            mockStatus,
            status
        )
    }
}
