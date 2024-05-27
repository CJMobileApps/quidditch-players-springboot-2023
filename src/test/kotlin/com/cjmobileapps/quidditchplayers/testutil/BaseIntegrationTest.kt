package com.cjmobileapps.quidditchplayers.testutil

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.json.JacksonTester
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.client.RestTemplate

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class BaseIntegrationTest {
    val restTemplate by lazy { RestTemplate() }

    @LocalServerPort
    val localSeverPort = 0

    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    lateinit var mapper: ObjectMapper

    val localHostUri by lazy { "http://localhost:$localSeverPort" }

    @BeforeEach
    fun setupBefore() {
        JacksonTester.initFields(this, mapper)
    }
}
