package com.example.running.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.running.db.Run
import com.example.running.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    val lastRun = repository.getLastRun()

    fun insertRun(run: Run) = viewModelScope.launch {
        repository.insertRun(run)
    }

}