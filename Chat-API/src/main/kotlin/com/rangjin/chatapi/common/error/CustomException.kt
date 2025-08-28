package com.rangjin.chatapi.common.error

data class CustomException(

    val errorCode: ErrorCode

) : Exception(errorCode.message) {
}