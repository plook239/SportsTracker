package com.gmail.lookpj2.sportstracker.data.local

import androidx.lifecycle.LiveData
import com.gmail.lookpj2.sportstracker.data.local.dao.TeamDao
import com.gmail.lookpj2.sportstracker.data.local.entities.TeamEntity

class TeamRepository(private val teamDao: TeamDao) {
    val readAllData: LiveData<List<TeamEntity>> = teamDao.getTeams()

    fun addTeam(teamEntity: TeamEntity) {
        teamDao.insertTeam(teamEntity)
    }

    fun removeTeam(id: Int) {
        teamDao.deleteTeam(id)
    }
}