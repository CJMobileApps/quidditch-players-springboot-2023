package com.cjmobileapps.quidditchplayers.api.status.controller

import com.cjmobileapps.quidditchplayers.api.status.service.StatusService
import com.cjmobileapps.quidditchplayers.data.MockData
import com.cjmobileapps.quidditchplayers.data.model.Error
import com.cjmobileapps.quidditchplayers.data.model.HouseName
import com.cjmobileapps.quidditchplayers.data.model.ResponseEntityWrapper
import com.cjmobileapps.quidditchplayers.data.model.Status
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
import java.util.UUID

@AutoConfigureMockMvc
class StatusControllerTest : BaseIntegrationTest() {
    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private lateinit var mockMvc: MockMvc

    private lateinit var jsonHousesResponseEntityWrapper: JacksonTester<ResponseEntityWrapper<Status>>

    @MockBean
    private lateinit var mockStatusService: StatusService

    @Test
    fun `getStatusByPlayerId success call`(): Unit =
        runBlocking {
            // given
            val mockStatus =
                Status(
                    playerId = UUID.fromString("fd1f2deb-9637-4214-b991-a1b8daf18a7b"),
                    status = MockData.getStatuses(name = "Harry Potter", "Defense Against The Dark Arts").first(),
                )

            val response =
                ResponseEntityWrapper(
                    data = mockStatus,
                    error = null,
                    statusCode = HttpStatus.OK.value(),
                )

            val responseJsonHousesResponseEntityWrapper = jsonHousesResponseEntityWrapper.write(response).json

            // when
            Mockito.`when`(mockStatusService.getStatusByPlayerId(UUID.fromString("fd1f2deb-9637-4214-b991-a1b8daf18a7b")))
                .thenReturn(mockStatus)

            // verify
            val mvcResult =
                mockMvc.perform(
                    MockMvcRequestBuilders
                        .get("/api/v1/quidditchplayers/player/status/fd1f2deb-9637-4214-b991-a1b8daf18a7b")
                        .contentType(MediaType.APPLICATION_JSON),
                )
                    .andExpect(MockMvcResultMatchers.request().asyncStarted())
                    .andReturn()

            mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().json(responseJsonHousesResponseEntityWrapper))
        }

    @Test
    fun `getStatusByPlayerId throw clientException`(): Unit =
        runBlocking {
            // given
            val response =
                ResponseEntityWrapper<Status>(
                    data = null,
                    error =
                        Error(
                            isError = true,
                            message = "Status not found",
                        ),
                    statusCode = HttpStatus.BAD_REQUEST.value(),
                )

            val responseJsonHousesResponseEntityWrapper = jsonHousesResponseEntityWrapper.write(response).json

            // when
            Mockito.`when`(mockStatusService.getStatusByPlayerId(UUID.fromString("fd1f2deb-9637-4214-b991-a1b8daf18a7b")))
                .thenThrow(ClientException("Status not found"))

            // verify
            val mvcResult =
                mockMvc.perform(
                    MockMvcRequestBuilders
                        .get("/api/v1/quidditchplayers/player/status/fd1f2deb-9637-4214-b991-a1b8daf18a7b")
                        .contentType(MediaType.APPLICATION_JSON),
                )
                    .andExpect(MockMvcResultMatchers.request().asyncStarted())
                    .andReturn()

            mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError)
                .andExpect(MockMvcResultMatchers.content().json(responseJsonHousesResponseEntityWrapper))
        }

    @Test
    fun `getStatusByPlayerId throw internalException`(): Unit =
        runBlocking {
            // given
            val response =
                ResponseEntityWrapper<Status>(
                    data = null,
                    error =
                        Error(
                            isError = true,
                            message = "Status not found",
                        ),
                    statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                )

            val responseJsonHousesResponseEntityWrapper = jsonHousesResponseEntityWrapper.write(response).json

            // when
            Mockito.`when`(mockStatusService.getStatusByPlayerId(UUID.fromString("fd1f2deb-9637-4214-b991-a1b8daf18a7b")))
                .thenThrow(InternalException("Status not found"))

            // verify
            val mvcResult =
                mockMvc.perform(
                    MockMvcRequestBuilders
                        .get("/api/v1/quidditchplayers/player/status/fd1f2deb-9637-4214-b991-a1b8daf18a7b")
                        .contentType(MediaType.APPLICATION_JSON),
                )
                    .andExpect(MockMvcResultMatchers.request().asyncStarted())
                    .andReturn()

            mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is5xxServerError)
                .andExpect(MockMvcResultMatchers.content().json(responseJsonHousesResponseEntityWrapper))
        }

    @Test
    fun `getStatusByHouseName success call`(): Unit =
        runBlocking {
            // given
            val mockStatus =
                Status(
                    playerId = UUID.fromString("fd1f2deb-9637-4214-b991-a1b8daf18a7b"),
                    status = MockData.getStatuses(name = "Harry Potter", "Defense Against The Dark Arts").first(),
                )

            val response =
                ResponseEntityWrapper(
                    data = mockStatus,
                    error = null,
                    statusCode = HttpStatus.OK.value(),
                )

            val responseJsonHousesResponseEntityWrapper = jsonHousesResponseEntityWrapper.write(response).json

            // when
            Mockito.`when`(mockStatusService.getStatusByHouseName(HouseName.GRYFFINDOR.name))
                .thenReturn(mockStatus)

            // verify
            val mvcResult =
                mockMvc.perform(
                    MockMvcRequestBuilders
                        .get("/api/v1/quidditchplayers/player/status")
                        .param("houseName", HouseName.GRYFFINDOR.name)
                        .contentType(MediaType.APPLICATION_JSON),
                )
                    .andExpect(MockMvcResultMatchers.request().asyncStarted())
                    .andReturn()

            mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().json(responseJsonHousesResponseEntityWrapper))
        }

    @Test
    fun `getStatusByHouseName throw clientException`(): Unit =
        runBlocking {
            // given
            val response =
                ResponseEntityWrapper<Status>(
                    data = null,
                    error =
                        Error(
                            isError = true,
                            message = "Status not found",
                        ),
                    statusCode = HttpStatus.BAD_REQUEST.value(),
                )

            val responseJsonHousesResponseEntityWrapper = jsonHousesResponseEntityWrapper.write(response).json

            // when
            Mockito.`when`(mockStatusService.getStatusByHouseName(HouseName.GRYFFINDOR.name))
                .thenThrow(ClientException("Status not found"))

            // verify
            val mvcResult =
                mockMvc.perform(
                    MockMvcRequestBuilders
                        .get("/api/v1/quidditchplayers/player/status")
                        .param("houseName", HouseName.GRYFFINDOR.name)
                        .contentType(MediaType.APPLICATION_JSON),
                )
                    .andExpect(MockMvcResultMatchers.request().asyncStarted())
                    .andReturn()

            mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError)
                .andExpect(MockMvcResultMatchers.content().json(responseJsonHousesResponseEntityWrapper))
        }

    @Test
    fun `getStatusByHouseName throw internalException`(): Unit =
        runBlocking {
            // given
            val response =
                ResponseEntityWrapper<Status>(
                    data = null,
                    error =
                        Error(
                            isError = true,
                            message = "Status not found",
                        ),
                    statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                )

            val responseJsonHousesResponseEntityWrapper = jsonHousesResponseEntityWrapper.write(response).json

            // when
            Mockito.`when`(mockStatusService.getStatusByHouseName(HouseName.GRYFFINDOR.name))
                .thenThrow(InternalException("Status not found"))

            // verify
            val mvcResult =
                mockMvc.perform(
                    MockMvcRequestBuilders
                        .get("/api/v1/quidditchplayers/player/status")
                        .param("houseName", HouseName.GRYFFINDOR.name)
                        .contentType(MediaType.APPLICATION_JSON),
                )
                    .andExpect(MockMvcResultMatchers.request().asyncStarted())
                    .andReturn()

            mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is5xxServerError)
                .andExpect(MockMvcResultMatchers.content().json(responseJsonHousesResponseEntityWrapper))
        }
}
