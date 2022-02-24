package com.example.logisticappswift.adapters

import androidx.recyclerview.widget.RecyclerView
import com.example.logisticappswift.databinding.DriverJobListBinding
import com.example.logisticappswift.objects.CreatedPost
import com.example.logisticappswift.objects.JobListClickListener
import com.squareup.picasso.Picasso

class DriverJobListCardViewHolder(
    private val cellBinding: DriverJobListBinding,
    private val clickListener: JobListClickListener
):RecyclerView.ViewHolder(cellBinding.root){

    fun bindPost(post:CreatedPost){
        cellBinding.jobTitleTxt.text = post.title
        cellBinding.jobLoadTxt.text = post.load
        Picasso.get().load(post.profile_img).into(cellBinding.postedByProfileImg)
        cellBinding.postedByTxt.text = post.posted_by
        cellBinding.jobListCardView.setOnClickListener{
            clickListener.onPostClick(post)
        }
    }
}