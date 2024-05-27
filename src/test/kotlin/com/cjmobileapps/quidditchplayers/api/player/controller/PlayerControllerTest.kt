package com.cjmobileapps.quidditchplayers.api.player.controller

import com.cjmobileapps.quidditchplayers.api.player.service.PlayerService
import com.cjmobileapps.quidditchplayers.data.MockData
import com.cjmobileapps.quidditchplayers.data.model.Error
import com.cjmobileapps.quidditchplayers.data.model.HouseName
import com.cjmobileapps.quidditchplayers.data.model.Player
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
class PlayerControllerTest : BaseIntegrationTest() {
    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private lateinit var mockMvc: MockMvc

    private lateinit var jsonPlayerResponseEntityWrapper: JacksonTester<ResponseEntityWrapper<List<Player>>>

    @MockBean
    private lateinit var mockPlayerService: PlayerService

    @Test
    fun `getPlayers from Ravenclaw house then success call`(): Unit =
        runBlocking {
            // given
            val response =
                ResponseEntityWrapper(
                    data = MockData.ravenclawTeam(),
                    error = null,
                    statusCode = HttpStatus.OK.value(),
                )

            val responseJsonPlayerResponseEntityWrapper = jsonPlayerResponseEntityWrapper.write(response).json

            // When
            Mockito.`when`(mockPlayerService.getPlayers(HouseName.RAVENCLAW.name)).thenReturn(MockData.ravenclawTeam())

            // verify
            val mvcResult =
                mockMvc.perform(
                    MockMvcRequestBuilders
                        .get("/api/v1/quidditchplayers/player")
                        .param("houseName", HouseName.RAVENCLAW.name)
                        .contentType(MediaType.APPLICATION_JSON),
                )
                    .andExpect(MockMvcResultMatchers.request().asyncStarted())
                    .andReturn()

            mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().json(responseJsonPlayerResponseEntityWrapper))
        }

    @Test
    fun `getPlayers from Ravenclaw house then throw clientException`(): Unit =
        runBlocking {
            // given
            val response =
                ResponseEntityWrapper<List<Player>>(
                    data = null,
                    error =
                        Error(
                            isError = true,
                            message = "Could not find players",
                        ),
                    statusCode = HttpStatus.BAD_REQUEST.value(),
                )

            val responseJsonPlayerResponseEntityWrapper = jsonPlayerResponseEntityWrapper.write(response).json

            // when
            Mockito.`when`(mockPlayerService.getPlayers(HouseName.RAVENCLAW.name)).thenThrow(ClientException("Could not find players"))

            // Verify
            val mvcResult =
                mockMvc.perform(
                    MockMvcRequestBuilders
                        .get("/api/v1/quidditchplayers/player")
                        .param("houseName", HouseName.RAVENCLAW.name)
                        .contentType(MediaType.APPLICATION_JSON),
                )
                    .andExpect(MockMvcResultMatchers.request().asyncStarted())
                    .andReturn()

            mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError)
                .andExpect(MockMvcResultMatchers.content().json(responseJsonPlayerResponseEntityWrapper))
        }

    @Test
    fun `getPlayers from Ravenclaw house then throw internalException`(): Unit =
        runBlocking {
            // given
            val response =
                ResponseEntityWrapper<List<Player>>(
                    data = null,
                    error =
                        Error(
                            isError = true,
                            message = "Could not find players",
                        ),
                    statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                )

            val responseJsonPlayerResponseEntityWrapper = jsonPlayerResponseEntityWrapper.write(response).json

            // when
            Mockito.`when`(mockPlayerService.getPlayers(HouseName.RAVENCLAW.name)).thenThrow(InternalException("Could not find players"))

            // Verify
            val mvcResult =
                mockMvc.perform(
                    MockMvcRequestBuilders
                        .get("/api/v1/quidditchplayers/player")
                        .param("houseName", HouseName.RAVENCLAW.name)
                        .contentType(MediaType.APPLICATION_JSON),
                )
                    .andExpect(MockMvcResultMatchers.request().asyncStarted())
                    .andReturn()

            mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is5xxServerError)
                .andExpect(MockMvcResultMatchers.content().json(responseJsonPlayerResponseEntityWrapper))
        }

    @Test
    fun `getPlayers no house param then success call`(): Unit =
        runBlocking {
            // given
            val response =
                ResponseEntityWrapper(
                    data = MockData.ravenclawTeam(),
                    error = null,
                    statusCode = HttpStatus.OK.value(),
                )

            val responseJsonPlayerResponseEntityWrapper = jsonPlayerResponseEntityWrapper.write(response).json

            // When
            Mockito.`when`(mockPlayerService.getPlayers(null)).thenReturn(MockData.ravenclawTeam())

            // verify
            val mvcResult =
                mockMvc.perform(
                    MockMvcRequestBuilders
                        .get("/api/v1/quidditchplayers/player")
                        .contentType(MediaType.APPLICATION_JSON),
                )
                    .andExpect(MockMvcResultMatchers.request().asyncStarted())
                    .andReturn()

            mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().json(responseJsonPlayerResponseEntityWrapper))
        }

    @Test
    fun `getPlayers no house param then throw clientException`(): Unit =
        runBlocking {
            // given
            val response =
                ResponseEntityWrapper<List<Player>>(
                    data = null,
                    error =
                        Error(
                            isError = true,
                            message = "Could not find players",
                        ),
                    statusCode = HttpStatus.BAD_REQUEST.value(),
                )

            val responseJsonPlayerResponseEntityWrapper = jsonPlayerResponseEntityWrapper.write(response).json

            // when
            Mockito.`when`(mockPlayerService.getPlayers(null)).thenThrow(ClientException("Could not find players"))

            // Verify
            val mvcResult =
                mockMvc.perform(
                    MockMvcRequestBuilders
                        .get("/api/v1/quidditchplayers/player")
                        .contentType(MediaType.APPLICATION_JSON),
                )
                    .andExpect(MockMvcResultMatchers.request().asyncStarted())
                    .andReturn()

            mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError)
                .andExpect(MockMvcResultMatchers.content().json(responseJsonPlayerResponseEntityWrapper))
        }

//
    @Test
    fun `getPlayers no house param then throw internalException`(): Unit =
        runBlocking {
            // given
            val response =
                ResponseEntityWrapper<List<Player>>(
                    data = null,
                    error =
                        Error(
                            isError = true,
                            message = "Could not find players",
                        ),
                    statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                )

            val responseJsonPlayerResponseEntityWrapper = jsonPlayerResponseEntityWrapper.write(response).json

            // when
            Mockito.`when`(mockPlayerService.getPlayers(null)).thenThrow(InternalException("Could not find players"))

            // Verify
            val mvcResult =
                mockMvc.perform(
                    MockMvcRequestBuilders
                        .get("/api/v1/quidditchplayers/player")
                        .contentType(MediaType.APPLICATION_JSON),
                )
                    .andExpect(MockMvcResultMatchers.request().asyncStarted())
                    .andReturn()

            mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is5xxServerError)
                .andExpect(MockMvcResultMatchers.content().json(responseJsonPlayerResponseEntityWrapper))
        }
}
