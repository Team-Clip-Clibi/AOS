package com.oneThing.domain.tokenManger

interface TokenMangerController {
    suspend fun requestUpdateToken(refreshToken : String) : Boolean
}