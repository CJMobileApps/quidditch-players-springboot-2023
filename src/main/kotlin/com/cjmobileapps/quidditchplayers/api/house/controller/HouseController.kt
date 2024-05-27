package com.cjmobileapps.quidditchplayers.api.house.controller

import com.cjmobileapps.quidditchplayers.api.house.service.HouseService
import com.cjmobileapps.quidditchplayers.data.model.House
import com.cjmobileapps.quidditchplayers.data.model.ResponseEntityWrapper
import com.cjmobileapps.quidditchplayers.data.model.toResponseEntity
import com.cjmobileapps.quidditchplayers.util.ClientException
import com.cjmobileapps.quidditchplayers.util.InternalException
import org.springframework.http.CacheControl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.TimeUnit

@RequestMapping("api/v1/quidditchplayers/house")
@RestController
class HouseController(
    val houseService: HouseService,
) {
    @GetMapping
    suspend fun getAllHouses(): ResponseEntity<ResponseEntityWrapper<List<House>>> {
        return try {
            val response =
                ResponseEntityWrapper(
                    data = houseService.getAllHouses(),
                    statusCode = HttpStatus.OK.value(),
                )

            ResponseEntity
                .status(HttpStatus.OK)
                .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
                .body(response)
        } catch (clientException: ClientException) {
            clientException.toResponseEntity()
        } catch (internalException: InternalException) {
            internalException.toResponseEntity()
        }
    }
}
