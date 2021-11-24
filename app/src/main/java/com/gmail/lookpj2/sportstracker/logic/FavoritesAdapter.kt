package com.gmail.lookpj2.sportstracker.logic

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.gmail.lookpj2.sportstracker.R
import com.gmail.lookpj2.sportstracker.data.local.entities.TeamEntity
import com.gmail.lookpj2.sportstracker.ui.EventsFragment
import com.gmail.lookpj2.sportstracker.ui.MainActivity

class FavoritesAdapter(
    private var teams: List<TeamEntity>
) : RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {
    private lateinit var context: Context
    private lateinit var teamViewModel: TeamViewModel
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        context = parent.context
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_favorite_team, parent, false)
        return FavoritesViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind(teams[position])
    }

    override fun getItemCount(): Int = teams.size

    fun submitList(list: List<TeamEntity>){
        teams = list
        notifyDataSetChanged()
    }

    inner class FavoritesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val badge: ImageView = itemView.findViewById(R.id.team_badge)
        val name: TextView = itemView.findViewById(R.id.team_name)
        val remove: ImageButton = itemView.findViewById(R.id.favorite_remove_button)

        fun bind(team: TeamEntity) {
            Glide.with(itemView)
                .load(team.badgeUrl)
                .transform(CenterCrop())
                .into(badge)

            name.text = team.teamName

            itemView.setOnClickListener {
                val activity = context as MainActivity
                val fragmentManager = activity.supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(
                    R.id.event_fragment_container,
                    EventsFragment(team.teamId.toString())
                )
                fragmentTransaction.commit()
            }

            remove.setOnClickListener {
                if (context is MainActivity) {
                    val activity = context as MainActivity
                    teamViewModel = ViewModelProvider(activity)
                        .get(TeamViewModel::class.java)
                    teamViewModel.deleteTeam(team.teamId)
                    notifyItemRemoved(layoutPosition)
                    notifyItemRangeChanged(layoutPosition, itemCount)
                }
            }

        }
    }
}