package com.example.running.db

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "running_table")
data class Run(
    var img: Bitmap? = null,
    var timeStamp: Long = 0L,      // when the run started
    var avgSpeedInKMH: Int = 0,
    var distanceInMeters: Int = 0,
    var timeInMillis: Long = 0L,       // the run time
    var caloriesBurned: Int = 0
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
