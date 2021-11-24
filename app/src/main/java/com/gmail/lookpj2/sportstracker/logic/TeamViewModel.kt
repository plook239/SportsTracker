package com.gmail.lookpj2.sportstracker.logic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.gmail.lookpj2.sportstracker.data.local.TeamRepository
import com.gmail.lookpj2.sportstracker.data.local.database.SportsTrackerDb
import com.gmail.lookpj2.sportstracker.data.local.entities.TeamEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TeamViewModel(application: Application) : AndroidViewModel(application) {

    private val teamRepo: TeamRepository

    init {
        val teamDao = SportsTrackerDb.getInstance(application).getTeamDao()
        teamRepo = TeamRepository(teamDao)
    }

    fun addTeam(teamEntity: TeamEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            teamRepo.addTeam(teamEntity)
        }
    }

    fun getTeams(): LiveData<List<TeamEntity>> {
        return teamRepo.readAllData
    }

    fun deleteTeam(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            teamRepo.removeTeam(id)
        }
    }
}