package com.gmail.lookpj2.sportstracker.ui

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmail.lookpj2.sportstracker.R
import com.gmail.lookpj2.sportstracker.data.Repository
import com.gmail.lookpj2.sportstracker.data.local.entities.TeamEntity
import com.gmail.lookpj2.sportstracker.data.remote.model.PastEventModel
import com.gmail.lookpj2.sportstracker.data.remote.model.TeamModel
import com.gmail.lookpj2.sportstracker.databinding.ActivityMainBinding
import com.gmail.lookpj2.sportstracker.logic.TeamViewModel
import com.gmail.lookpj2.sportstracker.logic.TeamsAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    private lateinit var teamResults: RecyclerView
    private lateinit var teamResultsAdapter: TeamsAdapter

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSearch()
        setupButtons()
        setupRecyclerView()

        teamResultsAdapter = TeamsAdapter(listOf()) { team -> showTeamEvents(team) }
        teamResults.adapter = teamResultsAdapter
    }

    private fun setupSearch(){
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

    private fun setupButtons(){
        binding.favoritesFragmentStartButton.setOnClickListener{
            startFavoritesFragment(FavoritesFragment())
            Log.d("Click", "Favorites Button Clicked")
        }

        binding.endFavoritesFragmentButton.setOnClickListener{
            endFavoritesFragment()
            Log.d("Click", "Close Favorites Button Clicked")
        }
    }

    private fun setupRecyclerView() {
        teamResults = team_recycler_view
        teamResults.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        val itemDecoration = DividerItemDecoration(this@MainActivity,
                DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(resources.getDrawable(R.drawable.layer, null))
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
        Log.d("MainActivity", "Teams found: $teams")
        teamResultsAdapter.updateTeams(teams)
    }

    private fun onTeamsFetchedError() {
        Toast.makeText(this, getString(R.string.error_fetch_teams),
                Toast.LENGTH_SHORT).show()
        Log.d("Crashed", "Internet is out or team name is wrong")
    }

    private fun showTeamEvents(teams: TeamModel) {
        Log.d("Click", "Selected Team ID: ${teams.teamId} + ${teams.teamId.toInt()} ")
        startEventsFragment(EventsFragment(teams.teamId))
    }
}
