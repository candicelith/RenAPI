package com.example.renapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.renapi.model.Distilleries

class DistilleriesAdapter(private val distilleriesList: List<Distilleries>) :
    RecyclerView.Adapter<DistilleriesAdapter.DistilleriesViewHolder>() {

    class DistilleriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.txt_distilleries_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DistilleriesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_distilleries, parent, false
        )
        return DistilleriesViewHolder(view)
    }

    override fun getItemCount() = distilleriesList.size

    override fun onBindViewHolder(holder: DistilleriesViewHolder, position: Int) {
        val distilleriesItem = distilleriesList[position]
        holder.textView.text = distilleriesItem.name
    }
}
