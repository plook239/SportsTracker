package com.gmail.lookpj2.sportstracker.logic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.gmail.lookpj2.sportstracker.data.local.database.SportsTrackerDb
import com.gmail.lookpj2.sportstracker.data.local.entities.TeamEntity
import com.gmail.lookpj2.sportstracker.data.remote.model.TeamModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TeamViewModel(application: Application): AndroidViewModel(application) {

    private val repo: TeamRepository

    init {
        val teamDao = SportsTrackerDb.getInstance(application).getTeamDao()
        repo = TeamRepository(teamDao)
    }

    fun addTeam(teamEntity: TeamEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repo.addTeam(teamEntity)
        }
    }

    fun getTeams(): List<TeamEntity> {
        val teamData = repo.readAllData
        return teamData
    }

    fun deleteTeam(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repo.removeTeam(id)
        }
    }
}