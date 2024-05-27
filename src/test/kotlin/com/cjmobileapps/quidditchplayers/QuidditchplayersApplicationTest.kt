package com.cjmobileapps.quidditchplayers

import com.cjmobileapps.quidditchplayers.data.MockData
import com.cjmobileapps.quidditchplayers.data.model.House
import com.cjmobileapps.quidditchplayers.data.model.HouseName
import com.cjmobileapps.quidditchplayers.data.model.Player
import com.cjmobileapps.quidditchplayers.data.model.Position
import com.cjmobileapps.quidditchplayers.data.model.ResponseEntityWrapper
import com.cjmobileapps.quidditchplayers.data.model.Status
import com.cjmobileapps.quidditchplayers.testutil.BaseIntegrationTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.json.JacksonTester
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.util.UriComponentsBuilder

@AutoConfigureMockMvc
class QuidditchplayersApplicationTest : BaseIntegrationTest() {
    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var context: WebApplicationContext

    private lateinit var jsonHousesResponseEntityWrapper: JacksonTester<ResponseEntityWrapper<List<House>>>

    private lateinit var jsonPlayerResponseEntityWrapper: JacksonTester<ResponseEntityWrapper<List<Player>>>

    private lateinit var jsonPositionsResponseEntityWrapper: JacksonTester<ResponseEntityWrapper<Map<Int, Position>>>

    private lateinit var jsonStatusResponseEntityWrapper: JacksonTester<ResponseEntityWrapper<Status>>

    @BeforeEach
    fun setup() {
        mockMvc =
            MockMvcBuilders
                .webAppContextSetup(context)
                .build()
    }

    @Test
    fun `get houses, players the player status happy flow`(): Unit =
        runBlocking {
            // given
            val headers = HttpHeaders()
            headers.set("Accept", "application/json")
            val httpEntity = HttpEntity(null, headers)

            // when houseApiResponse
            val houseApiResponse =
                withContext(Dispatchers.IO) {
                    restTemplate.exchange(
                        "$localHostUri/api/v1/quidditchplayers/house",
                        HttpMethod.GET,
                        httpEntity,
                        String::class.java,
                    )
                }

            val housesResponseEntityWrapper = jsonHousesResponseEntityWrapper.parse(houseApiResponse.body).`object`
            val houses = housesResponseEntityWrapper.data

            // verify houses
            Assertions.assertEquals(
                MockData.houses,
                houses,
            )

            // given for players
            val ravenClawHouse = houses?.first { it.name == HouseName.RAVENCLAW }

            val playerUri =
                UriComponentsBuilder.fromHttpUrl("$localHostUri/api/v1/quidditchplayers/player")
                    .queryParam("houseName", ravenClawHouse?.name?.name)
                    .toUriString()

            // when playersApiResponse
            val playersApiResponse =
                withContext(Dispatchers.IO) {
                    restTemplate.exchange(
                        playerUri,
                        HttpMethod.GET,
                        httpEntity,
                        String::class.java,
                    )
                }

            val playerResponseEntityWrapper = jsonPlayerResponseEntityWrapper.parse(playersApiResponse.body).`object`
            val players = playerResponseEntityWrapper.data
            val player = players?.first()

            // verify players
            Assertions.assertEquals(
                MockData.ravenclawTeam(),
                players,
            )

            // when positionsApiResponse
            val positionsApiResponse =
                withContext(Dispatchers.IO) {
                    restTemplate.exchange(
                        "$localHostUri/api/v1/quidditchplayers/position",
                        HttpMethod.GET,
                        httpEntity,
                        String::class.java,
                    )
                }

            val positionsResponseEntityWrapper =
                jsonPositionsResponseEntityWrapper.parse(positionsApiResponse.body).`object`
            val positions = positionsResponseEntityWrapper.data

            // verify positions
            Assertions.assertEquals(
                MockData.positions,
                positions,
            )
            Assertions.assertEquals(
                "Seeker",
                positions?.get(player?.position)?.positionName,
            )

            val statusUri =
                UriComponentsBuilder.fromHttpUrl("$localHostUri/api/v1/quidditchplayers/player/status/${player?.id}")
                    .toUriString()

            // when playersApiResponse
            val statusApiResponse =
                withContext(Dispatchers.IO) {
                    restTemplate.exchange(
                        statusUri,
                        HttpMethod.GET,
                        httpEntity,
                        String::class.java,
                    )
                }

            val statusResponseEntityWrapper =
                jsonStatusResponseEntityWrapper.parse(statusApiResponse.body).`object`
            val status = statusResponseEntityWrapper.data

            // verify status
            Assertions.assertTrue(status?.status?.isNotEmpty()!!)
            Assertions.assertEquals(
                player?.id,
                status.playerId,
            )
        }
}
