package com.clip.domain.useCase

import com.clip.domain.TOKEN_FORM
import com.clip.domain.UseCase
import com.clip.domain.model.HomeBanner
import com.clip.domain.model.NetworkResult
import com.clip.domain.model.NotificationType
import com.clip.domain.repository.DatabaseRepository
import com.clip.domain.repository.NetworkRepository
import com.clip.domain.tokenManger.TokenMangerController
import javax.inject.Inject

class GetHomeBanner @Inject constructor(
    private val database: DatabaseRepository,
    private val tokenManger: TokenMangerController,
    private val network: NetworkRepository,
) {
    sealed interface Result : UseCase.Result {
        data class Success(val data: ArrayList<HomeBanner>) : Result
        data class Fail(val errorMessage: String) : Result
    }

    suspend fun invoke(): Result {
        val token = database.getToken()
        val response = network.requestHomeBanner(
            token = TOKEN_FORM + token.first
        )
        when (response) {
            is NetworkResult.Success -> {
                /**
                 * TODO 배포 시 다시 정상으로
                 */
                val result = fakeHomeBanners()
//                val data = sortHomeBanner(result)
                return Result.Success(result)
            }

            is NetworkResult.Error -> {
                when (response.code) {
                    401 -> {
                        val refreshToken = tokenManger.requestUpdateToken(token.second)
                        if (!refreshToken) return Result.Fail("reLogin")
                        val newToken = database.getToken()
                        val reRequest = network.requestHomeBanner(
                            token = TOKEN_FORM + newToken.first
                        )
                        if (reRequest is NetworkResult.Success) {
                            val result = reRequest.data
                            val data = sortHomeBanner(result)
                            return Result.Success(data)
                        }
                        return Result.Fail("network error")
                    }

                    else -> return Result.Fail("network error")
                }
            }
        }
    }

    private fun sortHomeBanner(response: List<Pair<Int, String>>): ArrayList<HomeBanner> {
        val data = ArrayList(response.map { apiData ->
            HomeBanner(
                id = apiData.first,
                notificationBannerType = NotificationType.from(apiData.second)
            )
        })
        data.sortedBy { type -> type.notificationBannerType.priority }
        return data
    }

    private fun fakeHomeBanners(): ArrayList<HomeBanner> = arrayListOf(
        HomeBanner(1, NotificationType.MATCHING),
        HomeBanner(2, NotificationType.MATCHING),
        HomeBanner(3, NotificationType.MATCHING),

        HomeBanner(4, NotificationType.MATCHING_INFO),
        HomeBanner(5, NotificationType.MATCHING_INFO),
        HomeBanner(6, NotificationType.MATCHING_INFO),

        HomeBanner(7, NotificationType.REVIEW),
        HomeBanner(8, NotificationType.REVIEW),
        HomeBanner(9, NotificationType.REVIEW),
    ).also { list ->
        list.sortBy { it.notificationBannerType.priority }
    }

}