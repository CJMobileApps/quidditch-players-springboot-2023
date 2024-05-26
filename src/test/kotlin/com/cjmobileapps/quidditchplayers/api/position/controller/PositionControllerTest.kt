package com.cjmobileapps.quidditchplayers.api.position.controller

import com.cjmobileapps.quidditchplayers.api.position.service.PositionService
import com.cjmobileapps.quidditchplayers.data.MockData
import com.cjmobileapps.quidditchplayers.data.model.Error
import com.cjmobileapps.quidditchplayers.data.model.Position
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
class PositionControllerTest : BaseIntegrationTest() {

    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private lateinit var mockMvc: MockMvc

    private lateinit var jsonPositionResponseEntityWrapper: JacksonTester<ResponseEntityWrapper<Map<Int, Position>>>

    @MockBean
    private lateinit var mockPositionService: PositionService

    @Test
    fun `getPositions success call`(): Unit = runBlocking {

        // given
        val response = ResponseEntityWrapper(
            data = MockData.positions,
            error = null,
            statusCode = HttpStatus.OK.value()
        )

        val responseJsonPositionResponseEntityWrapper = jsonPositionResponseEntityWrapper.write(response).json

        // when
        Mockito.`when`(mockPositionService.getPositions()).thenReturn(MockData.positions)


        // verify
        val mvcResult = mockMvc.perform(
            MockMvcRequestBuilders
                .get("/api/v1/quidditchplayers/position")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.request().asyncStarted())
            .andReturn()

        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(responseJsonPositionResponseEntityWrapper))
    }

    @Test
    fun `getPositions throw clientException`(): Unit = runBlocking {

        // given
        val response = ResponseEntityWrapper<Map<Int, Position>>(
            data = null,
            error = Error(
                isError = true,
                message = "Could not find positions"
            ),
            statusCode = HttpStatus.BAD_REQUEST.value()
        )

        val responseJsonPositionResponseEntityWrapper = jsonPositionResponseEntityWrapper.write(response).json

        // when
        Mockito.`when`(mockPositionService.getPositions()).thenThrow(ClientException("Could not find positions"))


        // verify
        val mvcResult = mockMvc.perform(
            MockMvcRequestBuilders
                .get("/api/v1/quidditchplayers/position")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.request().asyncStarted())
            .andReturn()

        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().is4xxClientError)
            .andExpect(MockMvcResultMatchers.content().json(responseJsonPositionResponseEntityWrapper))
    }

    @Test
    fun `getAllHouses throw internalException`(): Unit = runBlocking {
        // given
        val response = ResponseEntityWrapper<Map<Int, Position>>(
            data = null,
            error = Error(
                isError = true,
                message = "Could not find positions"
            ),
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value()
        )

        val responseJsonPositionResponseEntityWrapper = jsonPositionResponseEntityWrapper.write(response).json

        // when
        Mockito.`when`(mockPositionService.getPositions()).thenThrow(InternalException("Could not find positions"))


        // verify
        val mvcResult = mockMvc.perform(
            MockMvcRequestBuilders
                .get("/api/v1/quidditchplayers/position")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.request().asyncStarted())
            .andReturn()

        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().is5xxServerError)
            .andExpect(MockMvcResultMatchers.content().json(responseJsonPositionResponseEntityWrapper))
    }
}
