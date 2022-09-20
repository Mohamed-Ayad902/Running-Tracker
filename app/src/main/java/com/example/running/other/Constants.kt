package com.example.running.other

import android.graphics.Color

object Constants {
    const val DATABASE_NAME = "running_db"

    const val REQUEST_CODE_LOCATION = 0

    const val ACTION_START_RESUME = "ACTION_START_RESUME"
    const val ACTION_PAUSE = "ACTION_PAUSE"
    const val ACTION_STOP = "ACTION_STOP"
    const val ACTION_SHOW_TRACKING_FRAGMENT = "ACTION_SHOW_TRACKING_FRAGMENT"

    const val SHARED_PREFERENCE_NAME = "SHARED_PREFERENCE_NAME"
    const val KEY_NAME = "KEY_NAME"
    const val KEY_WEIGHT = "KEY_WEIGHT"
    const val KEY_IS_FIRST_TIME = "KEY_IS_FIRST_TIME"

    const val LOCATION_UPDATE_INTERVAL = 5000L
    const val LOCATION_FASTEST_INTERVAL = 2000L
    const val POLYLINE_COLOR = Color.GREEN
    const val POLYLINE_WIDTH = 8f
    const val CAMERA_ZOOM = 15f

    const val TIME_UPDATE_INTERVAL = 90L

    const val NOTIFICATION_CHANNEL_ID = "tracking_channel"
    const val NOTIFICATION_CHANNEL_NAME = "Tracking"
    const val NOTIFICATION_ID = 1


}