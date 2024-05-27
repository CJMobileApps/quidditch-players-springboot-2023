package com.cjmobileapps.quidditchplayers.api.player.service

import com.cjmobileapps.quidditchplayers.api.player.repository.PlayerDao
import com.cjmobileapps.quidditchplayers.data.MockData
import com.cjmobileapps.quidditchplayers.data.model.HouseName
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PlayerServiceTest {
    @Mock
    lateinit var mockPlayerDao: PlayerDao

    lateinit var playerService: PlayerService

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        playerService = PlayerServiceImpl(mockPlayerDao)
    }

    @Test
    fun `getAllPlayers from ravenclaw house`(): Unit =
        runBlocking {
            // when
            Mockito.`when`(mockPlayerDao.getPlayers(HouseName.RAVENCLAW.name)).thenReturn(MockData.ravenclawTeam())

            // then
            val players = playerService.getPlayers(HouseName.RAVENCLAW.name)

            // verify
            Assertions.assertEquals(
                MockData.ravenclawTeam(),
                players,
            )
        }

    @Test
    fun `getAllPlayers with no house provided`(): Unit =
        runBlocking {
            // when
            Mockito.`when`(mockPlayerDao.getPlayers(null)).thenReturn(MockData.allQuidditchTeam)

            // then
            val players = playerService.getPlayers(null)

            // verify
            Assertions.assertEquals(
                MockData.allQuidditchTeam,
                players,
            )
        }
}
