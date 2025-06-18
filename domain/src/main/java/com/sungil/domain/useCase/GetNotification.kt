package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.model.NetworkResult
import com.sungil.domain.model.NotificationData
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import com.sungil.domain.tokenManger.TokenMangerController
import javax.inject.Inject

class GetNotification @Inject constructor(
    private val database: DatabaseRepository,
    private val network: NetworkRepository,
    private val tokenManger : TokenMangerController
) {

    sealed interface Result : UseCase.Result {
        data class Success(val data: List<NotificationData>) : Result
        data class Fail(val errorMessage: String) : Result
    }

    suspend fun invoke(): Result {
        val token = database.getToken()
        return when(val notification = network.requestNotification(TOKEN_FORM + token.first)){
            is NetworkResult.Success -> {
                Result.Success(notification.data.notificationDataList)
            }

            is NetworkResult.Error -> Result.Fail("network error")
        }
//        when (notification.responseCode) {
//            401 -> {
//                val refreshToken = tokenManger.requestUpdateToken(token.second)
//                if (!refreshToken) return Result.Fail("reLogin")
//                val newToken = database.getToken()
//                val retry = network.requestNotification(TOKEN_FORM + newToken.first)
//                when (retry.responseCode) {
//                    200 -> {
//                        return Result.Success(retry.notificationDataList)
//                    }
//
//                    204 -> {
//                        /**
//                         * TODO -> 배포시 서버 데이터로만 출력
//                         */
//                        return Result.Success(
//                            listOf(
//                                NotificationData(
//                                    noticeType = "NOTICE",
//                                    content = "테스트에용",
//                                    link = "www.naver.com"
//                                ),
//                                NotificationData(
//                                    noticeType = "ARTICLE",
//                                    content = "테스트에용2",
//                                    link = "www.naver.com"
//                                ),
//                                NotificationData(
//                                    noticeType = "NOTICE",
//                                    content = "테스트에용3",
//                                    link = "www.naver.com"
//                                ),
//                            )
//                        )
//                    }
//                    else -> return Result.Fail("network error")
//                }
//            }
//
//            200 -> {
//                return Result.Success(notification.notificationDataList)
//            }
//
//            204 -> {
//                return Result.Success(
//                    listOf(
//                        NotificationData(
//                            noticeType = "NOTICE",
//                            content = "테스트에용",
//                            link = "www.naver.com"
//                        ),
//                        NotificationData(
//                            noticeType = "ARTICLE",
//                            content = "테스트에용2",
//                            link = "www.naver.com"
//                        ),
//                        NotificationData(
//                            noticeType = "NOTICE",
//                            content = "테스트에용3",
//                            link = "www.naver.com"
//                        ),
//                    )
//                )
//            }
//
//            else -> return Result.Fail("network error")
//        }
    }
}