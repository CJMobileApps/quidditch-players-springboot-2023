package com.cjmobileapps.quidditchplayers.util

data class InternalException(override val message: String = Constants.INTERNAL_SERVER_ERROR) : Exception(message)

data class ClientException(override val message: String = Constants.YOU_SENT_A_BAD_REQUEST) : Exception(message)
