package com.example.renapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DistilleriesAdapter(
    private val distilleries: List<String>,
    private val favoriteDistilleries: Set<String>,
    private val onLikeClicked: (String, Boolean) -> Unit
    ) : RecyclerView.Adapter<DistilleriesAdapter.DistilleriesViewHolder>() {

    private val likedItems = favoriteDistilleries.toMutableSet()

    class DistilleriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.txt_distilleries_name)
        val likeIcon: ImageView = itemView.findViewById(R.id.icon_like)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DistilleriesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_distilleries, parent, false
        )
        return DistilleriesViewHolder(view)
    }

    override fun onBindViewHolder(holder: DistilleriesViewHolder, position: Int) {
        val distilleriesItem = distilleries[position]
        holder.textView.text = distilleriesItem

        val isLiked = likedItems.contains(distilleriesItem)
        holder.likeIcon.setImageResource(
            if (isLiked) {
                R.drawable.baseline_favorite_24
            } else {
                R.drawable.baseline_favorite_border_24
            }
        )

        holder.likeIcon.setOnClickListener {
            val newLikeState = !isLiked
            if (newLikeState) {
                likedItems.add(distilleriesItem)
            } else {
                likedItems.remove(distilleriesItem)
            }
            onLikeClicked(distilleriesItem, newLikeState)
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int = distilleries.size

}
