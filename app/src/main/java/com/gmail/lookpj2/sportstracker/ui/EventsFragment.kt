package com.gmail.lookpj2.sportstracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.gmail.lookpj2.sportstracker.R
import com.gmail.lookpj2.sportstracker.data.Repository
import com.gmail.lookpj2.sportstracker.data.remote.model.PastEventModel
import com.gmail.lookpj2.sportstracker.databinding.EventsFragmentBinding
import kotlinx.android.synthetic.main.events_fragment.*

class EventsFragment(id: String) : Fragment() {
    var teamId = id
    lateinit var binding: EventsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EventsFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Repository.getTeamsPastFiveEvents(
            teamId,
            onSuccess = ::onEventsFetched,
            onError = ::onEventsError
        )

        close_event_fragment_button.setOnClickListener {
            endEventsFragment()
        }
    }

    private fun onEventsError() {
        if (isAdded) {
            Toast.makeText(
                this@EventsFragment.context, getString(R.string.error_fetch_teams),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun onEventsFetched(pastEvents: List<PastEventModel>) {
        if (isAdded) {
            binding.eventViewTitle.text =
                getString(R.string.event_view_title, pastEvents[0].homeTeam)

            pastEvents.forEachIndexed { i: Int, pastEventModel: PastEventModel ->
                when (i) {
                    0 -> {
                        binding.textView1.text = getString(
                            R.string.game_data_1, pastEvents[0].homeTeam,
                            pastEvents[0].homeTeamScore,
                            pastEvents[0].awayTeam, pastEvents[0].awayTeamScore
                        )
                        binding.textView1.visibility = View.VISIBLE
                    }
                    1 -> {
                        binding.textView2.text = getString(
                            R.string.game_data_2, pastEvents[1].homeTeam,
                            pastEvents[1].homeTeamScore,
                            pastEvents[1].awayTeam, pastEvents[1].awayTeamScore
                        )
                        binding.textView2.visibility = View.VISIBLE
                    }
                    2 -> {
                        binding.textView3.text = getString(
                            R.string.game_data_3, pastEvents[2].homeTeam,
                            pastEvents[2].homeTeamScore,
                            pastEvents[2].awayTeam, pastEvents[2].awayTeamScore
                        )
                        binding.textView3.visibility = View.VISIBLE
                    }
                    3 -> {
                        binding.textView4.text = getString(
                            R.string.game_data_4, pastEvents[3].homeTeam,
                            pastEvents[3].homeTeamScore,
                            pastEvents[3].awayTeam, pastEvents[3].awayTeamScore
                        )
                        binding.textView4.visibility = View.VISIBLE
                    }
                    4 -> {
                        binding.textView5.text = getString(
                            R.string.game_data_5, pastEvents[4].homeTeam,
                            pastEvents[4].homeTeamScore,
                            pastEvents[4].awayTeam, pastEvents[4].awayTeamScore
                        )
                        binding.textView5.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun endEventsFragment() {
        activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
    }
}