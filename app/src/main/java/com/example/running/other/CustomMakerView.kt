package com.example.running.other

import android.annotation.SuppressLint
import android.content.Context
import android.widget.TextView
import com.example.running.R
import com.example.running.db.Run
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import java.text.SimpleDateFormat
import java.util.*

class CustomMakerView(
    private val runs: List<Run>,
    c: Context,
    layoutId: Int
) :
    MarkerView(c, layoutId) {


    override fun getOffset(): MPPointF {
        return MPPointF(-width / 2f, -height.toFloat())
    }

    @SuppressLint("SetTextI18n")
    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        super.refreshContent(e, highlight)
        if (e == null) {
            return
        }
        val curRunId = e.x.toInt()
        val run = runs[curRunId]

        val calendar = Calendar.getInstance().apply {
            timeInMillis = run.timeStamp
        }
        val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
        findViewById<TextView>(R.id.marker_date).text = dateFormat.format(calendar.time)

        val speed = "${run.avgSpeedInKMH} km/h"
        findViewById<TextView>(R.id.marker_avgSpeed).text = speed

        val distanceInKm = " ${run.distanceInMeters / 1000f} km "
        findViewById<TextView>(R.id.marker_distance).text = distanceInKm

        findViewById<TextView>(R.id.marker_duration).text =
            TrackingUtility.getFormattedStopWatchTime(run.timeInMillis)

        val caloriesBurned = " ${run.caloriesBurned} kcal "
        findViewById<TextView>(R.id.marker_calories).text = caloriesBurned
    }
}
