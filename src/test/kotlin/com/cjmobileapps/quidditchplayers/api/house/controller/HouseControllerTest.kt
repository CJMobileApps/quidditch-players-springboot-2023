package com.cjmobileapps.quidditchplayers.api.house.controller

import com.cjmobileapps.quidditchplayers.api.house.service.HouseService
import com.cjmobileapps.quidditchplayers.data.MockData
import com.cjmobileapps.quidditchplayers.data.model.Error
import com.cjmobileapps.quidditchplayers.data.model.House
import com.cjmobileapps.quidditchplayers.data.model.ResponseEntityWrapper
import com.cjmobileapps.quidditchplayers.testutil.BaseIntegrationTest
import com.cjmobileapps.quidditchplayers.util.ClientException
import com.cjmobileapps.quidditchplayers.util.InternalException
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.json.JacksonTester
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@AutoConfigureMockMvc
class HouseControllerTest : BaseIntegrationTest() {

    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private lateinit var mockMvc: MockMvc

    private lateinit var jsonHousesResponseEntityWrapper: JacksonTester<ResponseEntityWrapper<List<House>>>

    @MockBean
    private lateinit var mockHouseService: HouseService



    @Test
    fun `getAllHouses success call`(): Unit = runBlocking {

        // given
        val response = ResponseEntityWrapper(
            data = MockData.houses,
            error = null,
            statusCode = HttpStatus.OK.value()
        )

        val responseJsonHousesResponseEntityWrapper = jsonHousesResponseEntityWrapper.write(response).json

        // when
        Mockito.`when`(mockHouseService.getAllHouses()).thenReturn(MockData.houses)


        // verify
        val mvcResult = mockMvc.perform(
            MockMvcRequestBuilders
                .get("/api/v1/quidditchplayers/house")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.request().asyncStarted())
            .andReturn()

        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(responseJsonHousesResponseEntityWrapper))
    }

    @Test
    fun `getAllHouses throw clientException`(): Unit = runBlocking {

        // given
        val response = ResponseEntityWrapper<List<House>>(
            data = null,
            error = Error(
                isError = true,
                message = "Could not find houses"
            ),
            statusCode = HttpStatus.BAD_REQUEST.value()
        )

        val responseJsonHousesResponseEntityWrapper = jsonHousesResponseEntityWrapper.write(response).json

        // when
        Mockito.`when`(mockHouseService.getAllHouses()).thenThrow(ClientException("Could not find houses"))


        // verify
        val mvcResult = mockMvc.perform(
            MockMvcRequestBuilders
                .get("/api/v1/quidditchplayers/house")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.request().asyncStarted())
            .andReturn()

        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().is4xxClientError)
            .andExpect(MockMvcResultMatchers.content().json(responseJsonHousesResponseEntityWrapper))
    }

    @Test
    fun `getAllHouses throw internalException`(): Unit = runBlocking {
        // given
        val response = ResponseEntityWrapper<List<House>>(
            data = null,
            error = Error(
                isError = true,
                message = "Could not find houses"
            ),
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value()
        )

        val responseJsonHousesResponseEntityWrapper = jsonHousesResponseEntityWrapper.write(response).json

        // when
        Mockito.`when`(mockHouseService.getAllHouses()).thenThrow(InternalException("Could not find houses"))


        // verify
        val mvcResult = mockMvc.perform(
            MockMvcRequestBuilders
                .get("/api/v1/quidditchplayers/house")
            .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.request().asyncStarted())
            .andReturn()

        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().is5xxServerError)
            .andExpect(MockMvcResultMatchers.content().json(responseJsonHousesResponseEntityWrapper))
    }
}
