package com.gmail.lookpj2.sportstracker.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.gmail.lookpj2.sportstracker.data.local.entities.TeamEntity
import com.gmail.lookpj2.sportstracker.data.remote.model.TeamModel

@Dao
interface TeamDao {
    @Insert(onConflict = REPLACE)
    fun insertTeam(team: TeamEntity)

    @Update(onConflict = REPLACE)
    fun updateTeam(team: TeamEntity)

    @Query("DELETE FROM team_table WHERE teamId =:id")
    fun deleteTeam(id: Int)

    @Query("SELECT * FROM team_table ORDER BY teamName ASC")
    fun getTeams(): List<TeamEntity>
}