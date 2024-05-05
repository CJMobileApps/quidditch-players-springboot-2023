package com.cjmobileapps.quidditchplayers.api.player.controller

import com.cjmobileapps.quidditchplayers.api.player.service.PlayerService
import com.cjmobileapps.quidditchplayers.data.model.Player
import com.cjmobileapps.quidditchplayers.data.model.ResponseEntityWrapper
import com.cjmobileapps.quidditchplayers.data.model.toResponseEntity
import com.cjmobileapps.quidditchplayers.util.ClientException
import com.cjmobileapps.quidditchplayers.util.InternalException
import org.springframework.http.CacheControl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.TimeUnit

@RequestMapping("api/v1/quidditchplayers/player")
@RestController
class PlayerController(
    val playerService: PlayerService
) {

    @GetMapping
    suspend fun getPlayers(
        @RequestParam houseName: String?
    ): ResponseEntity<ResponseEntityWrapper<List<Player>>> {
        return try {
            val response = ResponseEntityWrapper(
                data = playerService.getPlayers(houseName),
                statusCode = HttpStatus.OK.value()
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
