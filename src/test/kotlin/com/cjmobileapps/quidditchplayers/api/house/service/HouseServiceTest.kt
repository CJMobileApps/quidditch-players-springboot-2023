package com.cjmobileapps.quidditchplayers.api.house.service

import com.cjmobileapps.quidditchplayers.api.house.repository.HouseDao
import com.cjmobileapps.quidditchplayers.data.MockData
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class HouseServiceTest {
    @Mock
    lateinit var mockHouseDao: HouseDao

    lateinit var houseService: HouseService

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        houseService = HouseServiceImpl(mockHouseDao)
    }

    @Test
    fun `getAllHouses success call`(): Unit =
        runBlocking {
            // when
            Mockito.`when`(mockHouseDao.getAllHouses()).thenReturn(MockData.houses)

            // then
            val houses = houseService.getAllHouses()

            // verify
            Assertions.assertEquals(
                MockData.houses,
                houses,
            )
        }
}
