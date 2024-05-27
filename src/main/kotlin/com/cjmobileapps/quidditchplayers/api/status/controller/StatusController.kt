package com.cjmobileapps.quidditchplayers.api.status.controller

import com.cjmobileapps.quidditchplayers.api.status.service.StatusService
import com.cjmobileapps.quidditchplayers.data.model.ResponseEntityWrapper
import com.cjmobileapps.quidditchplayers.data.model.Status
import com.cjmobileapps.quidditchplayers.data.model.toResponseEntity
import com.cjmobileapps.quidditchplayers.util.ClientException
import com.cjmobileapps.quidditchplayers.util.InternalException
import org.springframework.http.CacheControl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID
import java.util.concurrent.TimeUnit

@RequestMapping("api/v1/quidditchplayers/player/status")
@RestController
class StatusController(
    val statusService: StatusService,
) {
    @GetMapping
    suspend fun getStatusByHouseName(
        @RequestParam houseName: String?,
    ): ResponseEntity<ResponseEntityWrapper<Status>> {
        return try {
            val response =
                ResponseEntityWrapper(
                    data = statusService.getStatusByHouseName(houseName),
                    statusCode = HttpStatus.OK.value(),
                )

            ResponseEntity
                .status(HttpStatus.OK)
                .cacheControl(CacheControl.maxAge(1, TimeUnit.SECONDS))
                .eTag(response.hashCode().toString())
                .body(response)
        } catch (clientException: ClientException) {
            clientException.toResponseEntity()
        } catch (internalException: InternalException) {
            internalException.toResponseEntity()
        }
    }

    @GetMapping(path = ["{id}"])
    suspend fun getStatusByPlayerId(
        @PathVariable("id") id: UUID,
    ): ResponseEntity<ResponseEntityWrapper<Status>> {
        return try {
            val response =
                ResponseEntityWrapper(
                    data = statusService.getStatusByPlayerId(id),
                    statusCode = HttpStatus.OK.value(),
                )

            ResponseEntity
                .status(HttpStatus.OK)
                .cacheControl(CacheControl.maxAge(1, TimeUnit.SECONDS))
                .eTag(response.hashCode().toString())
                .body(response)
        } catch (clientException: ClientException) {
            clientException.toResponseEntity()
        } catch (internalException: InternalException) {
            internalException.toResponseEntity()
        }
    }
}
