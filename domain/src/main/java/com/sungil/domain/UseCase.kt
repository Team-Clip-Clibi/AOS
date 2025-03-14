package com.sungil.domain

interface UseCase<PARAM : UseCase.Param, RESULT : UseCase.Result> {
    suspend fun invoke(param: PARAM): RESULT


    interface Param
    interface Result
}