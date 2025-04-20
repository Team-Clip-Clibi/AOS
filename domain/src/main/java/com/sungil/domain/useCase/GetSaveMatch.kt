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
        data class Success(val data: MatchData) : Result
        data class Fail(val errorMessage: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        val saveData = database.getSaveMatch()
        if (saveData.isEmpty()) {
            return Result.Fail("no data")
        }
        val serverIds: Set<Int> = (param.serverMatch.oneThingMatch.map { it.matchingId }
                + param.serverMatch.randomMatch.map { it.matchingId }).toSet()

        val sameIdMatches: List<SaveMatch> = saveData.filter { it.matchId in serverIds }
        val diffIdMatches: List<SaveMatch> = saveData.filter { it.matchId !in serverIds }
        if (diffIdMatches.isEmpty()) {
            saveData.forEach { match ->
                val removeResult = database.deleteSaveMatch(matchId = match.matchId)
                if (!removeResult) {
                    throw IllegalArgumentException("error delete match data")
                }
            }
            return Result.Fail("no data")
        }
        val sameInfos: List<MatchInfo> = sameIdMatches.map { it.toMatchInfo() }
        diffIdMatches.forEach { match ->
            val removeData = database.deleteSaveMatch(matchId = match.matchId)
            if (!removeData) {
                throw IllegalArgumentException("error delete match data")
            }
        }
        if (sameInfos.isEmpty()) {
            return Result.Fail("no data")
        }
        val oneThingMatch: List<MatchInfo> =
            sameInfos.filter { it.category == CATEGORY.CONTENT_ONE_THING }

        val randomMatch: List<MatchInfo> =
            sameInfos.filter { it.category == CATEGORY.CONTENT_RANDOM }
        val result = MatchData(
            oneThingMatch = oneThingMatch,
            randomMatch = randomMatch
        )
        return Result.Success(result)
    }

    private fun SaveMatch.toMatchInfo() = MatchInfo(
        category = CATEGORY.valueOf(this.category),
        matchingId = this.matchId,
        daysUntilMeeting = this.time.toIntOrNull() ?: 0,
        meetingPlace = this.location
    )
}