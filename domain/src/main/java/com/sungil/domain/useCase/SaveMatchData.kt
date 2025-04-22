package com.sungil.domain.useCase

import com.sungil.domain.CATEGORY
import com.sungil.domain.UseCase
import com.sungil.domain.model.MatchData
import com.sungil.domain.model.MatchInfo
import com.sungil.domain.repository.DatabaseRepository
import javax.inject.Inject

class SaveMatchData @Inject constructor(private val database: DatabaseRepository) :
    UseCase<SaveMatchData.Param, SaveMatchData.Result> {

    data class Param(
        val newSaveData: MatchInfo,
        val saveData: MatchData,
        val unSaveData: MatchData,
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(
            val newSaveData: MatchData,
            val newUnSaveData: MatchData,
        ) : Result

        data class Fail(val errorMessage: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        val result = database.insetSaveMatch(
            matchId = param.newSaveData.matchingId,
            time = param.newSaveData.daysUntilMeeting.toString(),
            category = param.newSaveData.category.name,
            location = param.newSaveData.meetingPlace
        )
        if (!result) {
            return Result.Fail("save Fail")
        }

        //  saveData + 추가된 데이터
        val updatedSaveList = (
                param.saveData.oneThingMatch + param.saveData.randomMatch + param.newSaveData
                ).sortedBy { sortCategoryPriority(it.category) }

        val newSaveData = MatchData(
            oneThingMatch = updatedSaveList.filter { it.category == CATEGORY.CONTENT_ONE_THING },
            randomMatch = updatedSaveList.filter { it.category == CATEGORY.CONTENT_RANDOM }
        )

        //  unSaveData - 제거된 데이터
        val updatedUnsaveList = (
                param.unSaveData.oneThingMatch + param.unSaveData.randomMatch
                ).filter { it.matchingId != param.newSaveData.matchingId }
            .sortedBy { sortCategoryPriority(it.category) }

        val newUnSaveData = MatchData(
            oneThingMatch = updatedUnsaveList.filter { it.category == CATEGORY.CONTENT_ONE_THING },
            randomMatch = updatedUnsaveList.filter { it.category == CATEGORY.CONTENT_RANDOM }
        )

        return Result.Success(
            newSaveData = newSaveData,
            newUnSaveData = newUnSaveData
        )
    }

    private fun sortCategoryPriority(category: CATEGORY): Int {
        return when (category) {
            CATEGORY.CONTENT_ONE_THING -> 0
            CATEGORY.CONTENT_RANDOM -> 1
        }
    }
}