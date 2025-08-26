package com.clip.network.model

import kotlinx.serialization.Serializable

@Serializable
data class RelationShip(
    val relationshipStatus: String?,
    val isSameRelationshipConsidered: Boolean?,
)