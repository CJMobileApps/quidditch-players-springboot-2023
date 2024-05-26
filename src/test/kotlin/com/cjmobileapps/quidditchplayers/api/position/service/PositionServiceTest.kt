package com.cjmobileapps.quidditchplayers.api.position.service

import com.cjmobileapps.quidditchplayers.api.position.repository.PositionDao
import com.cjmobileapps.quidditchplayers.data.MockData
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PositionServiceTest {

    @Mock
    lateinit var mockPositionDao: PositionDao

    lateinit var positionService: PositionService

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        positionService = PositionServiceImpl(mockPositionDao)
    }

    @Test
    fun `getAllHouses success call`(): Unit = runBlocking {

        // when
        Mockito.`when`(mockPositionDao.getPositions()).thenReturn(MockData.positions)

        // then
        val positions = positionService.getPositions()

        // verify
        Assertions.assertEquals(
            MockData.positions,
            positions
        )
    }
}
