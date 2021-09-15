package com.yunuscagliyan.weatherapp.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.yunuscagliyan.weatherapp.R
import com.yunuscagliyan.weatherapp.common.Resource
import com.yunuscagliyan.weatherapp.data.remote.model.Current
import com.yunuscagliyan.weatherapp.data.remote.model.WeatherResponse
import com.yunuscagliyan.weatherapp.data.remote.url.WeatherUrl
import com.yunuscagliyan.weatherapp.databinding.FragmentDetailBinding
import com.yunuscagliyan.weatherapp.presentation.MainActivity
import com.yunuscagliyan.weatherapp.presentation.detail.viewmodel.DetailViewModel
import com.yunuscagliyan.weatherapp.presentation.home.adapter.WeatherDayAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var binding: FragmentDetailBinding? = null
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var adapter:WeatherDayAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        adapter= WeatherDayAdapter()

        initUI()
        viewModel.weatherResource.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding?.progressCircular?.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding?.progressCircular?.visibility = View.GONE
                    bindCurrent(resource.data)
                    val result=resource.data
                    result?.daily?.let {
                        adapter.submitList(it)
                    }

                }
                is Resource.Error -> {
                    binding?.progressCircular?.visibility = View.GONE


                }
                else -> Unit
            }
        }

    }

    private fun bindCurrent(current: WeatherResponse?) {
        current?.let { weather ->
            binding?.apply {
                tvCityName.text = "${weather.timezone ?: ""}"
                Glide
                    .with(requireContext())
                    .load("${WeatherUrl.ICON_URL}${weather.current?.weather!![0].icon ?: ""}.png")
                    .centerCrop()
                    .into(ivCurrent)

                tvTemp.text = "${weather.current?.temp ?: 0}°C"
            }
        }

    }

    private fun initUI() {
        binding?.apply {
            (activity as? MainActivity)?.setUpToolbar(toolBar)
            rvDaily.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            rvDaily.adapter=adapter
        }
    }

}