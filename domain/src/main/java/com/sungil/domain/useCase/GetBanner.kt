package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.model.Banner
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import javax.inject.Inject

class GetBanner @Inject constructor(
    private val database: DatabaseRepository,
    private val network: NetworkRepository,
) : UseCase<GetBanner.Param, GetBanner.Result> {

    sealed interface Result : UseCase.Result {
        data class Success(val data: Banner) : Result
        data class Fail(val message: String) : Result
    }

    data class Param(val bannerHost: String) : UseCase.Param

    override suspend fun invoke(param: Param): Result {
        val token = database.getToken()
        if (token.first == null || token.second == null) {
            return Result.Fail("token is null")
        }
        val banner = network.requestBanner(TOKEN_FORM + token, param.bannerHost)
        if (banner.responseCode != 204) {
            return Result.Fail("network error")
        }
        return Result.Success(banner)
    }

}