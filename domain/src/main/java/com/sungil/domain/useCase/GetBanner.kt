package com.sungil.domain.useCase

import com.sungil.domain.BANNER_LOGIN
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
        val banner = network.requestBanner(
            accessToken = when (param.bannerHost) {
                BANNER_LOGIN -> ""
                else -> {
                    val token = database.getToken()
                    TOKEN_FORM + token.first
                }
            },
            bannerType = param.bannerHost
        )
        when (banner.first) {
            200 -> {
                if (banner.second.isEmpty()) {
                    return Result.Success(emptyList())
                }
                val data = reMakeHeadText(banner.second)
                return Result.Success(data)
            }

            401 -> {
                if (param.bannerHost == BANNER_LOGIN) {
                    return Result.Fail("network error")
                }
                val token = database.getToken()
                val refreshToken = tokenManger.requestUpdateToken(token.second)
                if (!refreshToken) return Result.Fail("reLogin")
                val newToken = database.getToken()
                val retry = network.requestBanner(
                    accessToken = TOKEN_FORM + newToken.first,
                    bannerType = param.bannerHost
                )
                if (banner.second.isEmpty()) {
                    return Result.Fail("no banner")
                }
                if (retry.first == 200) {
                    val data = reMakeHeadText(banner.second)
                    return Result.Success(data)
                }
                return Result.Fail("network error")
            }

            else -> return Result.Fail("network error")
        }
    }

    private fun reMakeHeadText(data: List<BannerData>): List<BannerData> {
        return data.map {
            it.copy(headText = it.headText?.replace("\\n", "\n") ?: "")
        }
    }
}