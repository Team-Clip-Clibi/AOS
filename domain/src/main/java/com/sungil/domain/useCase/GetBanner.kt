package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.model.BannerData
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import com.sungil.domain.tokenManger.TokenMangerController
import javax.inject.Inject

class GetBanner @Inject constructor(
    private val database: DatabaseRepository,
    private val network: NetworkRepository,
    private val tokenManger: TokenMangerController,
) : UseCase<GetBanner.Param, GetBanner.Result> {

    sealed interface Result : UseCase.Result {
        data class Success(val data: List<BannerData>) : Result
        data class Fail(val message: String) : Result
    }

    data class Param(val bannerHost: String) : UseCase.Param

    override suspend fun invoke(param: Param): Result {
        val token = database.getToken()
        val banner = network.requestBanner(
            accessToken = TOKEN_FORM + token.first,
            bannerType = param.bannerHost
        )
        when (banner.responseCode) {
            200 -> {
                if (banner.bannerResponse.isEmpty()) {
                    return Result.Fail("banner is null")
                }
                return Result.Success(banner.bannerResponse)
            }

            401 -> {
                val refreshToken = tokenManger.requestUpdateToken(token.second)
                if (!refreshToken) return Result.Fail("reLogin")
                val newToken = database.getToken()
                val retry = network.requestBanner(
                    accessToken = TOKEN_FORM + newToken.first,
                    bannerType = param.bannerHost
                )
                if (retry.responseCode == 200) {
                    return Result.Success(banner.bannerResponse)
                }
                if (retry.bannerResponse.isEmpty()) {
                    return Result.Fail("banner is null")
                }
                return Result.Fail("network error")
            }

            else -> return Result.Fail("network error")
        }
    }
}