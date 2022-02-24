package com.example.logisticappswift.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.logisticappswift.databinding.DriverJobListBinding
import com.example.logisticappswift.objects.CreatedPost
import com.example.logisticappswift.objects.JobListClickListener

class DriverJobListCardAdapter(private val post_list: List<CreatedPost>,private val clickListener: JobListClickListener
):RecyclerView.Adapter<DriverJobListCardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriverJobListCardViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = DriverJobListBinding.inflate(from, parent, false)
        return DriverJobListCardViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: DriverJobListCardViewHolder, position: Int) {
        holder.bindPost(post_list[position])
    }

    override fun getItemCount(): Int = post_list.size

}