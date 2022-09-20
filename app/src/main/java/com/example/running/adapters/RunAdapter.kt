package com.example.running.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.running.databinding.RunItemBinding
import com.example.running.db.Run
import com.example.running.other.TrackingUtility
import java.text.SimpleDateFormat
import java.util.*

class RunAdapter : RecyclerView.Adapter<RunAdapter.RunVH>() {

    inner class RunVH(val binding: RunItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Run>() {
        override fun areItemsTheSame(oldItem: Run, newItem: Run) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Run, newItem: Run) = oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RunVH(
            RunItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: RunVH, position: Int) {
        val run = differ.currentList[position]
        holder.binding.apply {
            Glide.with(holder.itemView).load(run.img).transform(CenterCrop(), RoundedCorners(10))
                .into(imageView)

            val calendar = Calendar.getInstance().apply {
                timeInMillis = run.timeStamp
            }
            val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
            date.text = dateFormat.format(calendar.time)

            val speed = "${run.avgSpeedInKMH} km/h"
            avgSpeed.text = speed

            val distanceInKm = " ${run.distanceInMeters / 1000f} km "
            distance.text = distanceInKm

            time.text = TrackingUtility.getFormattedStopWatchTime(run.timeInMillis)

            val caloriesBurned = " ${run.caloriesBurned} kcal "
            calories.text = caloriesBurned

        }
    }

    override fun getItemCount() = differ.currentList.size
}