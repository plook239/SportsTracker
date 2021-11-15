package com.gmail.lookpj2.sportstracker.logic

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.gmail.lookpj2.sportstracker.R
import com.gmail.lookpj2.sportstracker.data.local.entities.TeamEntity
import com.gmail.lookpj2.sportstracker.data.remote.model.TeamModel
import com.gmail.lookpj2.sportstracker.ui.MainActivity


class TeamsAdapter(
    private var teams: List<TeamModel>,
    private val onTeamClick: (team: TeamModel) -> Unit
) : RecyclerView.Adapter<TeamsAdapter.TeamViewHolder>() {
    private lateinit var context: Context
    private lateinit var mTeamViewModel: TeamViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        context = parent.context
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_team, parent, false)
        return TeamViewHolder(view)
    }

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(teams[position])
    }

    fun updateTeams(team: List<TeamModel>) {
        this.teams = team
        notifyDataSetChanged()
    }

    inner class TeamViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val badge: ImageView = itemView.findViewById(R.id.team_badge)
        private val name: TextView = itemView.findViewById(R.id.team_name)
        private val switch: SwitchCompat = itemView.findViewById(R.id.favorite_selector_switch)

        fun bind(team: TeamModel) {
            Glide.with(itemView)
                .load(team.badgeUrl)
                .transform(CenterCrop())
                .into(badge)

            name.text = team.teamName

            itemView.setOnClickListener { onTeamClick.invoke(team) }
            switch.setOnClickListener {
                Log.d("Click", "Switch clicked")
                if (switch.isChecked) {
                    Log.d("Click", "Add team to DB/Favorites")
                    if (context is MainActivity) {
                        val activity = context as MainActivity
                        mTeamViewModel = ViewModelProvider(activity).get(TeamViewModel::class.java)
                        mTeamViewModel.addTeam(
                            TeamEntity(
                                team.teamId.toInt(),
                                team.teamName, team.badgeUrl
                            )
                        )
                    }
                }
                switch.setOnCheckedChangeListener { _, isChecked ->
                    Log.d("Click", "Changed switch state")
                    if (!isChecked) {
                        Log.d("Click", "Remove team from DB/Favorites")
                        if (context is MainActivity) {
                            val activity = context as MainActivity
                            mTeamViewModel = ViewModelProvider(activity)
                                .get(TeamViewModel::class.java)
                            mTeamViewModel.deleteTeam(team.teamId.toInt())
                        }
                    }
                }
            }
        }
    }
}
