package com.gmail.lookpj2.sportstracker.logic

import androidx.lifecycle.LiveData
import com.gmail.lookpj2.sportstracker.data.local.dao.TeamDao
import com.gmail.lookpj2.sportstracker.data.local.entities.TeamEntity
import com.gmail.lookpj2.sportstracker.data.remote.model.TeamModel

class TeamRepository(private val teamDao: TeamDao) {
    val readAllData: List<TeamEntity> = teamDao.getTeams()

    suspend fun addTeam(teamEntity: TeamEntity){
        teamDao.insertTeam(teamEntity)
    }

    suspend fun removeTeam(id: Int){
        teamDao.deleteTeam(id)
    }
}