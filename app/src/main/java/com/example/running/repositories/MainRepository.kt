package com.example.running.repositories

import com.example.running.db.Run
import com.example.running.db.RunDao
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val dao: RunDao
) {

    suspend fun insertRun(run: Run) = dao.insertRun(run)

    suspend fun deleteRun(run: Run) = dao.deleteRun(run)

    fun getAllRunsSortedByDate() = dao.getAllRunSortedByDate()

    fun getLastRun() = dao.getLastRun()

    fun getAllRunsSortedByDistance() = dao.getAllRunSortedByDistance()

    fun getAllRunsSortedByTimeInMillis() = dao.getAllRunSortedByTimeInMillis()

    fun getAllRunsSortedByAvgSpeed() = dao.getAllRunSortedByAvgSpeed()

    fun getAllRunsSortedByCaloriesBurned() = dao.getAllRunSortedByCaloriesBurned()

    fun getTotalAvgSpeed() = dao.getTotalAvgSpeed()

    fun getTotalDistance() = dao.getTotalDistance()

    fun getTotalCaloriesBurned() = dao.getTotalCaloriesBurned()

    fun getTotalTimeInMillis() = dao.getTotalTimeInMillis()

}