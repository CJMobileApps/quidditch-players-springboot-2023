package com.cjmobileapps.quidditchplayers.api.house.repository

import com.cjmobileapps.quidditchplayers.data.MockData
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class HouseRepositoryTest {
    private lateinit var houseRepository: HouseRepository

    @BeforeEach
    private fun setup() {
        houseRepository = HouseRepository()
    }

    @Test
    fun `getAllHouses success call`(): Unit =
        runBlocking {
            // then
            val houses = houseRepository.getAllHouses()

            // verify
            Assertions.assertEquals(
                MockData.houses,
                houses,
            )
        }
}
