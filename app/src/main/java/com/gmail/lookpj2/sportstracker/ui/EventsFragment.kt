package com.gmail.lookpj2.sportstracker.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.gmail.lookpj2.sportstracker.R
import com.gmail.lookpj2.sportstracker.data.Repository
import com.gmail.lookpj2.sportstracker.data.remote.model.PastEventModel
import kotlinx.android.synthetic.main.events_fragment.*

class EventsFragment(id : String): Fragment() {
    private lateinit var viewOfLayout: View
    var teamId = id
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewOfLayout =
            LayoutInflater.from(context).inflate(R.layout.events_fragment, container,
                false)
        return viewOfLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Repository.getTeamsPastFiveEvents(
            teamId,
            onSuccess = ::onEventsFetched,
            onError = ::onEventsError
        )

        close_event_fragment_button.setOnClickListener{
            endEventsFragment()
        }
    }

    private fun onEventsError() {
        Toast.makeText(this@EventsFragment.context, getString(R.string.error_fetch_teams),
            Toast.LENGTH_SHORT).show()
        Log.d("Crashed", "No events were found with that team ID")
    }

    private fun onEventsFetched(pastEvents: List<PastEventModel>) {
        Log.d("Events", "Five Previous Home Events: $pastEvents[0]")
        val viewTitle = getString(R.string.event_view_title, pastEvents[0].homeTeam)
        event_view_title.text = viewTitle

        val gameOne = getString(R.string.game_data_1,pastEvents[0].homeTeam,
            pastEvents[0].homeTeamScore,
            pastEvents[0].awayTeam, pastEvents[0].awayTeamScore)
        textView1.text = gameOne

        val gameTwo = getString(R.string.game_data_2,pastEvents[1].homeTeam,
            pastEvents[1].homeTeamScore,
            pastEvents[1].awayTeam, pastEvents[1].awayTeamScore)
        textView2.text = gameTwo

        val gameThree = getString(R.string.game_data_3,pastEvents[2].homeTeam,
            pastEvents[2].homeTeamScore,
            pastEvents[2].awayTeam, pastEvents[2].awayTeamScore)
        textView3.text = gameThree

        val gameFour = getString(R.string.game_data_4,pastEvents[3].homeTeam,
            pastEvents[3].homeTeamScore,
            pastEvents[3].awayTeam, pastEvents[3].awayTeamScore)
        textView4.text = gameFour

        val gameFive = getString(R.string.game_data_5,pastEvents[4].homeTeam,
            pastEvents[4].homeTeamScore,
            pastEvents[4].awayTeam, pastEvents[4].awayTeamScore)
        textView5.text = gameFive

    }

    private fun endEventsFragment() {
        activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
    }
}