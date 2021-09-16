package com.yunuscagliyan.weatherapp.presentation.home

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.permissionx.guolindev.PermissionX
import com.yunuscagliyan.weatherapp.R
import com.yunuscagliyan.weatherapp.databinding.FragmentHomeBinding
import com.yunuscagliyan.weatherapp.presentation.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController=Navigation.findNavController(view)

        initUI()
        checkPermissionGranted()
        getUserCurrentLocation()
    }

    private fun initUI() {
        binding?.apply {
            btnEnter.setOnClickListener {
                viewModel.apiKey.value=etApiKey.text.toString()
                if(viewModel.validate()){
                    val action=HomeFragmentDirections.actionDestinationHomeToDestinationDetail(
                        viewModel.latitude.value,
                        viewModel.longitude.value,
                        viewModel.apiKey.value
                    )
                    navController.navigate(action)
                }else{
                    checkPermissionGranted()
                }

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
                    getString(R.string.permission_request_reason_text),
                    getString(R.string.btn_okay_text),
                    getString(R.string.btn_cancel_text)
                )
            }
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(
                    deniedList,
                    getString(R.string.permission_setting_text),
                    getString(R.string.btn_okay_text),
                    getString(R.string.btn_cancel_text)
                )
            }
            .request { allGranted, grantedList, deniedList ->
                if(allGranted){
                    getUserCurrentLocation()
                }
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