package com.sungil.domain.useCase

import com.sungil.domain.CATEGORY
import com.sungil.domain.UseCase
import com.sungil.domain.model.MatchData
import com.sungil.domain.model.MatchInfo
import com.sungil.domain.model.SaveMatch
import com.sungil.domain.repository.DatabaseRepository
import javax.inject.Inject

class GetSaveMatch @Inject constructor(private val database: DatabaseRepository) :
    UseCase<GetSaveMatch.Param, GetSaveMatch.Result> {

    data class Param(
        val serverMatch: MatchData,
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(
            val data: MatchData,          // 서버 & DB 공통
            val unSaveData: MatchData     // 서버에만 있는 데이터
        ) : Result

        data class Fail(val errorMessage: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        val saveData = database.getSaveMatch()
        val serverMatchList = param.serverMatch.oneThingMatch + param.serverMatch.randomMatch

        if (saveData.isEmpty()) {
            return Result.Fail("no data")
        }
        val serverIds = serverMatchList.map { it.matchingId }.toSet()
        val dbMatchMap = saveData.associateBy { it.matchId }
        // DB에만 있는 match → 삭제
        val deleteTargets = saveData.filter { it.matchId !in serverIds }
        deleteTargets.forEach { match ->
            val deleted = database.deleteSaveMatch(match.matchId)
            if (!deleted) throw IllegalArgumentException("error delete match data")
        }
        // 서버에만 있는 match → unSaveData
        val fromServerOnly: List<MatchInfo> = serverMatchList.filter {
            it.matchingId !in dbMatchMap.keys
        }
        // 서버와 DB에 모두 있는 match → DB 값 기준으로 변환
        val sameInfos: List<MatchInfo> = serverMatchList.mapNotNull {
            dbMatchMap[it.matchingId]?.toMatchInfo()
        }
        if (sameInfos.isEmpty() && fromServerOnly.isEmpty()) {
            return Result.Fail("no data")
        }
        val sortedSameInfos = sameInfos.sortedBy { sortCategoryPriority(it.category) }
        val sortedUnSaveInfos = fromServerOnly.sortedBy { sortCategoryPriority(it.category) }
        val data = MatchData(
            oneThingMatch = sortedSameInfos.filter { it.category == CATEGORY.CONTENT_ONE_THING },
            randomMatch = sortedSameInfos.filter { it.category == CATEGORY.CONTENT_RANDOM }
        )
        val unSaveData = MatchData(
            oneThingMatch = sortedUnSaveInfos.filter { it.category == CATEGORY.CONTENT_ONE_THING },
            randomMatch = sortedUnSaveInfos.filter { it.category == CATEGORY.CONTENT_RANDOM }
        )

        return Result.Success(data = data, unSaveData = unSaveData)
    }

    private fun SaveMatch.toMatchInfo() = MatchInfo(
        category = CATEGORY.valueOf(this.category),
        matchingId = this.matchId,
        daysUntilMeeting = this.time.toIntOrNull() ?: 0,
        meetingPlace = this.location
    )

    private fun sortCategoryPriority(category: CATEGORY): Int {
        return when (category) {
            CATEGORY.CONTENT_ONE_THING -> 0
            CATEGORY.CONTENT_RANDOM -> 1
        }
    }
}