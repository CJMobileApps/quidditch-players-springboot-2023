package com.cjmobileapps.quidditchplayers.api.position.repository

import com.cjmobileapps.quidditchplayers.data.MockData
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PositionRepositoryTest {

    private lateinit var positionRepository: PositionRepository

    @BeforeEach
    private fun setup() {
        positionRepository = PositionRepository()
    }

    @Test
    fun `getPositions success call`(): Unit = runBlocking {

        // then
        val positions = positionRepository.getPositions()

        // verify
        Assertions.assertEquals(
            MockData.positions,
            positions
        )
    }
}
