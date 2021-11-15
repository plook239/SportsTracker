package com.gmail.lookpj2.sportstracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.load.engine.Resource
import com.gmail.lookpj2.sportstracker.R
import com.gmail.lookpj2.sportstracker.logic.FavoritesAdapter
import com.gmail.lookpj2.sportstracker.logic.TeamViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.favorites_fragment.*

class FavoritesFragment : Fragment() {
    private lateinit var viewOfLayout: View
    private lateinit var mTeamViewModel: TeamViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewOfLayout =
            LayoutInflater.from(context).inflate(
                R.layout.favorites_fragment, container,
                false
            )
        return viewOfLayout
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        mTeamViewModel = ViewModelProvider(this).get(TeamViewModel::class.java)

        favorites_recycler_view.apply {
            layoutManager = LinearLayoutManager(
                activity, LinearLayoutManager.VERTICAL,
                false
            )
            val itemDecoration = DividerItemDecoration(
                this@FavoritesFragment.context,
                DividerItemDecoration.VERTICAL
            )
            itemDecoration.setDrawable(ResourcesCompat
                .getDrawable(resources, R.drawable.layer, null)!!)
            favorites_recycler_view.addItemDecoration(itemDecoration)
            adapter = FavoritesAdapter(mTeamViewModel.getTeams().toMutableList())
        }


    }

}