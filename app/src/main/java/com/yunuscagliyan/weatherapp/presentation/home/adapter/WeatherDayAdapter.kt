package com.yunuscagliyan.weatherapp.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yunuscagliyan.weatherapp.data.remote.model.Daily
import com.yunuscagliyan.weatherapp.data.remote.url.WeatherUrl
import com.yunuscagliyan.weatherapp.databinding.ItemDayWeatherBinding
import java.text.SimpleDateFormat
import java.util.*

class WeatherDayAdapter():RecyclerView.Adapter<WeatherDayAdapter.WeatherDayViewHolder>() {
    private var days:List<Daily?> = mutableListOf()

    fun submitList(list:List<Daily?>){
        this.days=list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherDayViewHolder {
        val binding=ItemDayWeatherBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return WeatherDayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherDayViewHolder, position: Int) {
        val daily=this.days[position]
        daily?.let {
            holder.bind(daily)
        }

    }

    override fun getItemCount(): Int {
        return this.days.size
    }

    class WeatherDayViewHolder(
        private val binding:ItemDayWeatherBinding
    ):RecyclerView.ViewHolder(binding.root){

        fun bind(daily: Daily){
            binding.apply {
                tvDayName.text=convertLongToTime(daily.dt?.toLong()?:0L)
                if(daily.weather?.isNotEmpty()==true){
                    val weather=daily.weather[0]
                    Glide
                        .with(itemView.context)
                        .load(WeatherUrl.ICON_URL+"${weather.icon?:""}.png")
                        .centerCrop()
                        .into(ivIcon)
                }
                tvMax.text="${daily.temp?.max?:0}°"
                tvMin.text="${daily.temp?.max?:0}°"
            }
        }
        fun convertLongToTime(time: Long): String {
            val date = Date(time*1000)
            val format = SimpleDateFormat("EEEE")
            return format.format(date)
        }
    }


}