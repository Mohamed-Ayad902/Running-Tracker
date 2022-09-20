package com.example.running.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.running.R
import com.example.running.databinding.FragmentSetupBinding
import com.example.running.other.Constants
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SetupFragment : Fragment() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @set:Inject
    var isFirstOpen = true

    private lateinit var binding: FragmentSetupBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSetupBinding.inflate(layoutInflater, container, false)
        if (!isFirstOpen) {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.setupFragment, true)
                .build()
            findNavController().navigate(
                R.id.action_setupFragment_to_runFragment, savedInstanceState, navOptions
            )
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext.setOnClickListener {
            if (getDataFromFields()) {
                findNavController().navigate(R.id.action_setupFragment_to_runFragment)
            } else {
                Snackbar.make(requireView(), "Please fill all fields", Snackbar.LENGTH_SHORT).show()
            }
        }

    }

    private fun getDataFromFields(): Boolean {
        binding.etName.error = null
        binding.etWeight.error = null

        if (binding.etName.editText?.text.toString().isEmpty()) {
            binding.etName.error = "Name is required"
            return false
        } else if (binding.etWeight.editText?.text.toString().isEmpty()) {
            binding.etWeight.error = "Weight is required"
            return false
        }

        val name = binding.etName.editText?.text.toString()
        val weight = binding.etWeight.editText?.text.toString().toFloat()

        sharedPreferences.edit()
            .putString(Constants.KEY_NAME, name)
            .putFloat(Constants.KEY_WEIGHT, weight)
            .putBoolean(Constants.KEY_IS_FIRST_TIME, false)
            .apply()
        return true
    }


}