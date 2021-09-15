package com.yunuscagliyan.weatherapp.presentation.home

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.permissionx.guolindev.PermissionX
import com.yunuscagliyan.weatherapp.databinding.FragmentHomeBinding
import com.yunuscagliyan.weatherapp.presentation.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        checkPermissionGranted()
    }

    private fun initUI() {
        viewModel.latitude.observe(viewLifecycleOwner) {
            binding?.apply {
                tvLocation.text = "latitude:${it}"
            }
        }
        viewModel.longitude.observe(viewLifecycleOwner) {
            binding?.apply {
                val text = tvLocation.text.toString()
                tvLocation.text = text + "\n longitude:${it}"
            }
        }

    }

    private fun checkPermissionGranted() {
        PermissionX.init(this)
            .permissions(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(
                    deniedList,
                    "Core fundamental are based on these permissions",
                    "OK",
                    "Cancel"
                )
            }
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(
                    deniedList,
                    "You need to allow necessary permissions in Settings manually",
                    "OK",
                    "Cancel"
                )
            }
            .request { allGranted, grantedList, deniedList ->
                getUserCurrentLocation()
            }
    }


    private fun getUserCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            this.fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(requireActivity())
            this.fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                viewModel.latitude.value = location?.latitude.toString()
                viewModel.longitude.value = location?.longitude.toString()
            }
        }
    }

}