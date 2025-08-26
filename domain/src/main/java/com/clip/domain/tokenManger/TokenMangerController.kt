package com.clip.domain.tokenManger

interface TokenMangerController {
    suspend fun requestUpdateToken(refreshToken : String) : Boolean
}