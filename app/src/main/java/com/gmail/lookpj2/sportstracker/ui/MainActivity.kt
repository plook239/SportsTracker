package com.gmail.lookpj2.sportstracker.ui

import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmail.lookpj2.sportstracker.R
import com.gmail.lookpj2.sportstracker.data.Repository
import com.gmail.lookpj2.sportstracker.data.local.entities.TeamEntity
import com.gmail.lookpj2.sportstracker.data.remote.model.TeamModel
import com.gmail.lookpj2.sportstracker.databinding.ActivityMainBinding
import com.gmail.lookpj2.sportstracker.logic.TeamViewModel
import com.gmail.lookpj2.sportstracker.logic.TeamsAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var teamResults: RecyclerView
    private lateinit var teamResultsAdapter: TeamsAdapter
    private lateinit var teamViewModel: TeamViewModel
    private lateinit var favoritesList: List<TeamEntity>

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        teamViewModel = ViewModelProvider(this)
            .get(TeamViewModel::class.java)

        setupSearch()
        setupButtons()
        setupRecyclerView()

        teamViewModel.getTeams().observe(this){
            favoritesList = it
        }

        teamResultsAdapter = TeamsAdapter(listOf()) { team -> showTeamEvents(team) }
        teamResults.adapter = teamResultsAdapter
    }

    private fun setupSearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                Repository.getSearchedTeams(
                    query.toString(),
                    onSuccess = ::onTeamsFetched,
                    onError = ::onTeamsFetchedError
                )
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                endFavoritesFragment()
                return false
            }
        })
    }

    private fun setupButtons() {
        binding.favoritesFragmentStartButton.setOnClickListener {
            startFavoritesFragment(FavoritesFragment())
        }

        binding.endFavoritesFragmentButton.setOnClickListener {
            endFavoritesFragment()
        }
    }

    private fun setupRecyclerView() {
        teamResults = team_recycler_view
        teamResults.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        val itemDecoration = DividerItemDecoration(
            this@MainActivity,
            DividerItemDecoration.VERTICAL
        )
        itemDecoration.setDrawable(ResourcesCompat
            .getDrawable(resources, R.drawable.layer, null)!!)
        teamResults.addItemDecoration(itemDecoration)
    }

    private fun startFavoritesFragment(favoritesFragment: FavoritesFragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.favorites_fragment_container, favoritesFragment)
        fragmentTransaction.commit()
    }

    private fun endFavoritesFragment() {
        val fragment: Fragment? =
            supportFragmentManager.findFragmentById(R.id.favorites_fragment_container)
        if (fragment != null) supportFragmentManager.beginTransaction().remove(fragment).commit()
    }

    private fun startEventsFragment(eventsFragment: EventsFragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.event_fragment_container, eventsFragment)
        fragmentTransaction.commit()
    }

    private fun onTeamsFetched(teams: List<TeamModel>) {
        teams.forEach { team ->
            favoritesList.forEach { favoriteTeam ->
                team.selectedFavorite =
                    team.selectedFavorite || team.teamId == favoriteTeam.teamId.toString()
            }
        }

        teamResultsAdapter.updateTeams(teams)
    }

    private fun onTeamsFetchedError() {
        Toast.makeText(
            this, getString(R.string.error_fetch_teams),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showTeamEvents(teams: TeamModel) {
        startEventsFragment(EventsFragment(teams.teamId))
    }
}
