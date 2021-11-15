package com.gmail.lookpj2.sportstracker.logic

import com.gmail.lookpj2.sportstracker.data.local.dao.TeamDao
import com.gmail.lookpj2.sportstracker.data.local.entities.TeamEntity

class TeamRepository(private val teamDao: TeamDao) {
    val readAllData: List<TeamEntity> = teamDao.getTeams()

    fun addTeam(teamEntity: TeamEntity) {
        teamDao.insertTeam(teamEntity)
    }

    fun removeTeam(id: Int) {
        teamDao.deleteTeam(id)
    }
}