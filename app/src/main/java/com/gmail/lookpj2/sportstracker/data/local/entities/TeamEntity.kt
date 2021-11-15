package com.gmail.lookpj2.sportstracker.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "team_table")
data class TeamEntity(
    @PrimaryKey(autoGenerate = true)
    val teamId: Int,
    val teamName: String,
    val badgeUrl: String
)
