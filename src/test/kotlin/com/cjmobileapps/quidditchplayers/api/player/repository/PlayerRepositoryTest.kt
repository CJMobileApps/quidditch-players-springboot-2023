package com.cjmobileapps.quidditchplayers.api.player.repository

import com.cjmobileapps.quidditchplayers.data.MockData
import com.cjmobileapps.quidditchplayers.data.model.HouseName
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PlayerRepositoryTest {
    private lateinit var playerRepository: PlayerRepository

    @BeforeEach
    private fun setup() {
        playerRepository = PlayerRepository()
    }

    @Test
    fun `getAllPlayers from ravenclaw house`(): Unit =
        runBlocking {
            val players = playerRepository.getPlayers(HouseName.RAVENCLAW.name)

            Assertions.assertEquals(
                MockData.ravenclawTeam(),
                players,
            )
        }

    @Test
    fun `getAllPlayers with no house`(): Unit =
        runBlocking {
            val players = playerRepository.getPlayers(null)

            Assertions.assertEquals(
                MockData.allQuidditchTeam,
                players,
            )
        }
}
