package com.example.tp3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tp3.R
import com.example.tp3.models.GlobalStatistics
import java.text.SimpleDateFormat
import java.util.*

class LeaderboardAdapter(private var list: ArrayList<GlobalStatistics>) :
    RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val score: TextView = view.findViewById(R.id.score)
        val date: TextView = view.findViewById(R.id.date)
        val layout: LinearLayout = view.findViewById(R.id.layoutContainer)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LeaderboardAdapter.ViewHolder {
        return LeaderboardAdapter.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.leaderboard_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = list[position]
        holder.name.text = "${position + 1}- ${current.name}"
        holder.score.text = "${current.score} points"
        holder.date.text = SimpleDateFormat("dd/MM/yyyy", Locale.CANADA).format(current.date)
    }

}
