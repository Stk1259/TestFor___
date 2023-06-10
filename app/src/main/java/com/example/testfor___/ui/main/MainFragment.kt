package com.example.testfor___.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.testfor___.R
import com.example.testfor___.databinding.FragmentMainBinding
import com.example.testfor___.location.LocationProvider
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainRecyclerAdapter: MainRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val locationTextView = binding.textLocation
        val dateTextView = binding.textDate
        val recyclerView = binding.recyclerCategories
        val avatarImageView = binding.imageAvatar

        val mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        val locationProvider = LocationProvider(requireContext())

        val currentDate = SimpleDateFormat("dd MMMM, yyyy", Locale("ru")).format(Date())
        dateTextView.text = currentDate

        val currentLocation = locationProvider.getCityName { cityName ->
            if (cityName != null) {
                binding.textLocation.text = cityName
            } else {
                Toast.makeText(requireContext(), "Нет доступа к геолокации", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        locationTextView.text = currentLocation

        Glide.with(requireContext()).load(R.drawable.avatar).circleCrop().into(avatarImageView)


        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        mainRecyclerAdapter = MainRecyclerAdapter(requireContext(), emptyList())
        recyclerView.adapter = mainRecyclerAdapter

        mainViewModel.categories.observe(viewLifecycleOwner) { categories ->
            mainRecyclerAdapter.categories = categories
            mainRecyclerAdapter.notifyDataSetChanged()
        }
        mainViewModel.loadCategories()

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestLocationPermission()
        } else {
            updateLocation()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun requestLocationPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                updateLocation()
            } else {
                // Permission denied, handle accordingly
                binding.textLocation.text = "Location permission denied"
            }
        }
    }

    private fun updateLocation() {
        val locationProvider = LocationProvider(requireContext())
        locationProvider.getCityName { cityName ->
            if (cityName != null) {
                binding.textLocation.text = cityName
            } else {
                Toast.makeText(requireContext(), "Нет доступа к геолокации", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    }
}