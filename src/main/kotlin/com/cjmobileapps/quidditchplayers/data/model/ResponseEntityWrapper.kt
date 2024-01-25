package com.cjmobileapps.quidditchplayers.data.model

import com.cjmobileapps.quidditchplayers.util.ClientException
import com.cjmobileapps.quidditchplayers.util.InternalException
import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ResponseEntityWrapper(
    val data: Any? = null,
    val error: Error? = null,
    val statusCode: Int
)

data class Error(
    val isError: Boolean = false,
    val message: String? = null
)

fun ClientException.toResponseEntity(): ResponseEntity<ResponseEntityWrapper> {
    val response = ResponseEntityWrapper(
        error = Error(isError = true, message = this.message),
        statusCode = HttpStatus.BAD_REQUEST.value()
    )

    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(response)
}

fun InternalException.toResponseEntity(): ResponseEntity<ResponseEntityWrapper> {
    val response = ResponseEntityWrapper(
        error = Error(isError = true, message = this.message),
        statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value()
    )

    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(response)
}
