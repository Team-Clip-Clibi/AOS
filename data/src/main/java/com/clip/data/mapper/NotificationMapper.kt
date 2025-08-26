package com.clip.data.mapper

import androidx.paging.PagingSource
import com.clip.domain.model.Notification
import com.clip.network.model.OneThingNotificationDTO
import retrofit2.Response

fun Response<List<OneThingNotificationDTO>>.toLoadResult(): PagingSource.LoadResult<String, Notification> {
    val data = body()?.map { it.toNotification() } ?: emptyList()
    val nextKey = data.lastOrNull()?.id?.toString()
    return PagingSource.LoadResult.Page(
        data = data,
        prevKey = null,
        nextKey = if (data.size < 50) null else nextKey
    )
}

fun OneThingNotificationDTO.toNotification(): Notification {
    return Notification(
        id = this.id,
        content = this.content,
        createdAt = this.createdAt,
        notificationType = this.notificationType
    )
}