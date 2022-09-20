package com.example.running.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.running.R
import com.example.running.adapters.RunAdapter
import com.example.running.databinding.FragmentStatisticsBinding
import com.example.running.other.CustomMakerView
import com.example.running.other.SortType
import com.example.running.other.TrackingUtility
import com.example.running.ui.viewModels.StatisticsViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.round
import kotlin.math.roundToInt

@AndroidEntryPoint
class StatisticsFragment : Fragment() {

    private val viewModel: StatisticsViewModel by viewModels()

    private lateinit var runAdapter: RunAdapter

    private lateinit var binding: FragmentStatisticsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatisticsBinding.inflate(layoutInflater, container, false)
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.options.adapter = adapter
        }
        setupBarChart()
        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            runAdapter = RunAdapter()
            layoutManager = LinearLayoutManager(requireContext())
            adapter = runAdapter
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToObservers()

        when (viewModel.sortType) {
            SortType.DATE -> binding.options.setSelection(0)
            SortType.RUNNING_TIME -> binding.options.setSelection(1)
            SortType.DISTANCE -> binding.options.setSelection(2)
            SortType.AVG_SPEED -> binding.options.setSelection(3)
            SortType.CALORIES_BURNED -> binding.options.setSelection(4)
        }


        binding.options.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                when (pos) {
                    0 -> viewModel.sortRuns(SortType.DATE)
                    1 -> viewModel.sortRuns(SortType.AVG_SPEED)
                    2 -> viewModel.sortRuns(SortType.RUNNING_TIME)
                    3 -> viewModel.sortRuns(SortType.DISTANCE)
                    4 -> viewModel.sortRuns(SortType.CALORIES_BURNED)
                }
                Log.e("mohamed", "onItemSelected: pos $pos")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        viewModel.runs.observe(viewLifecycleOwner) {
            runAdapter.differ.submitList(it)
            Log.d("mohamed", "onViewCreated: ${it.size} $it")
            it.forEach { run ->
                Log.d("mohamed", "onViewCreated: $run")
            }
        }
    }

    private fun setupBarChart() {
        binding.barChart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            setDrawLabels(false)
            axisLineColor = Color.GREEN
            textColor = Color.GREEN
            setDrawGridLines(false)
        }

        binding.barChart.axisLeft.apply {
            axisLineColor = Color.GREEN
            textColor = Color.GREEN
            setDrawGridLines(false)
        }

        binding.barChart.axisRight.apply {
            axisLineColor = Color.GREEN
            textColor = Color.GREEN
            setDrawGridLines(false)
        }

        binding.barChart.apply {
            description.text = "Avg Speed Over Time"
            legend.isEnabled = false
        }

    }

    private fun subscribeToObservers() {
        viewModel.totalTimeInMillis.observe(viewLifecycleOwner) {
            it?.let {
                val totalRunTime = TrackingUtility.getFormattedStopWatchTime(it)
                binding.tvTime.text = totalRunTime
            }
        }
        viewModel.totalDistance.observe(viewLifecycleOwner) {
            it?.let {
                val km = it / 1000f
                val totalDistance = (km * 10f).roundToInt() / 10f
                val totalDistanceString = "${totalDistance}km"
                binding.tvDistance.text = totalDistanceString
            }
        }
        viewModel.totalAvgSpeed.observe(viewLifecycleOwner) {
            it?.let {
                val avgSpeed = round(it * 10f) / 10f
                val avgSpeedString = "$avgSpeed km/h"
                binding.tvSpeed.text = avgSpeedString
            }
        }
        viewModel.totalCaloriesBurned.observe(viewLifecycleOwner) {
            it?.let {
                val totalCalories = "$it kcal"
                binding.tvCalories.text = totalCalories
            }
        }
        viewModel.runsSortedByDate.observe(viewLifecycleOwner) {
            it?.let {
                val allAvgSpeeds =
                    it.indices.map { i -> BarEntry(i.toFloat(), it[i].avgSpeedInKMH.toFloat()) }
                val barDataSet = BarDataSet(allAvgSpeeds, "Avg Speed Over Time").apply {
                }
                binding.barChart.data = BarData(barDataSet)
                binding.barChart.marker =
                    CustomMakerView(it.reversed(), requireContext(), R.layout.marker_view)
                binding.barChart.invalidate()
            }
        }
    }

}