package com.example.running.ui.fragments

import android.Manifest
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.running.R
import com.example.running.databinding.FragmentRunBinding
import com.example.running.db.Run
import com.example.running.other.Constants
import com.example.running.other.Constants.REQUEST_CODE_LOCATION
import com.example.running.other.TrackingUtility
import com.example.running.ui.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

@AndroidEntryPoint
class RunFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var sp: SharedPreferences

    private lateinit var binding: FragmentRunBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRunBinding.inflate(layoutInflater, container, false)
        requestPermissions()
        binding.btnStartWorkout.setOnClickListener {
            findNavController().navigate(R.id.action_runFragment_to_trackingFragment)
        }
        return binding.root
    }

    private fun setupCardViews(run: Run) {
        binding.apply {
            tvNoRuns.visibility = View.INVISIBLE

            cardTime.visibility = View.VISIBLE
            cardCalories.visibility = View.VISIBLE
            cardDistance.visibility = View.VISIBLE
            cardSpeed.visibility = View.VISIBLE

            val speed = "${run.avgSpeedInKMH} km/h"
            tvAvgSpeed.text = speed

            val distanceInKm = " ${run.distanceInMeters / 1000f} km "
            tvDistance.text = distanceInKm

            tvTime.text = TrackingUtility.getFormattedStopWatchTime(run.timeInMillis)

            val caloriesBurned = " ${run.caloriesBurned} kcal "
            tvCalories.text = caloriesBurned
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToObservers()
        binding.apply {
            val name = sp.getString(Constants.KEY_NAME, "")
            tvName.text = "Hello $name!"
        }
    }

    private fun subscribeToObservers() {
        // observe lastRun
        viewModel.lastRun.observe(viewLifecycleOwner) { run ->
            run?.let {
                setupCardViews(it)
            } ?: noRuns()
        }
    }

    private fun noRuns() = binding.apply {
        cardSpeed.visibility = View.INVISIBLE
        cardDistance.visibility = View.INVISIBLE
        cardCalories.visibility = View.INVISIBLE
        cardTime.visibility = View.INVISIBLE

        tvNoRuns.visibility = View.VISIBLE
    }

    private fun requestPermissions() {
        if (TrackingUtility.hasLocationPermissions(requireContext()))
            return

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                this,
                "Accept all permissions, mother fucker\nThis time I'm asking, next I'll just hack you and grant it",
                REQUEST_CODE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        } else {
            EasyPermissions.requestPermissions(
                this,
                "Accept all permissions, mother fucker\nThis time I'm asking, next I'll just hack you and grant it",
                REQUEST_CODE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {}

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermissions()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

}