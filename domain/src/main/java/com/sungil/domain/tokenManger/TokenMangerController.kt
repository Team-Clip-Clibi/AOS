package com.sungil.domain.tokenManger

interface TokenMangerController {
    suspend fun requestUpdateToken(refreshToken : String) : Boolean
}