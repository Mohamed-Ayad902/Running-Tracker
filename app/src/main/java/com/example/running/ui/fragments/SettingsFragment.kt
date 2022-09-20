package com.example.running.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.running.databinding.FragmentSettingsBinding
import com.example.running.other.Constants
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    @Inject
    lateinit var sp: SharedPreferences

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadSavedData()

        binding.btnSave.setOnClickListener {
            if (getDataFromFields()) {
                Snackbar.make(requireView(), "Changed successfully", Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(requireView(), "Please fill all fields", Snackbar.LENGTH_SHORT).show()
            }
        }

    }

    private fun loadSavedData() {
        binding.apply {
            etName.editText?.setText(sp.getString(Constants.KEY_NAME, ""))
            etWeight.editText?.setText(sp.getFloat(Constants.KEY_WEIGHT, 0f).toString())
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

        sp.edit()
            .putString(Constants.KEY_NAME, name)
            .putFloat(Constants.KEY_WEIGHT, weight)
            .apply()
        return true
    }

}