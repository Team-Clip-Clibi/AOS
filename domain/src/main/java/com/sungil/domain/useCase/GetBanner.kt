package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.model.BannerData
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import javax.inject.Inject

class GetBanner @Inject constructor(
    private val database: DatabaseRepository,
    private val network: NetworkRepository,
) : UseCase<GetBanner.Param, GetBanner.Result> {

    sealed interface Result : UseCase.Result {
        data class Success(val data: List<BannerData>) : Result
        data class Fail(val message: String) : Result
    }

    data class Param(val bannerHost: String) : UseCase.Param

    override suspend fun invoke(param: Param): Result {
        val token = database.getToken()
        val banner = network.requestBanner(TOKEN_FORM + token.first, param.bannerHost)
        when (banner.responseCode) {
            200 -> return Result.Success(banner.bannerResponse)
            401 -> {
                val refreshToken = network.requestUpdateToken(token.second)
                if (refreshToken.first != 200) {
                    return Result.Fail("reLogin")
                }
                val saveToken = database.setToken(refreshToken.second!!, refreshToken.third!!)
                if (!saveToken) {
                    return Result.Fail("save error")
                }
                val reRequest =
                    network.requestBanner(TOKEN_FORM + refreshToken.second, param.bannerHost)
                if (reRequest.responseCode != 200) {
                    return Result.Fail("network error")
                }
                return Result.Success(reRequest.bannerResponse)
            }

            else -> return Result.Fail("network error")
        }
    }

}