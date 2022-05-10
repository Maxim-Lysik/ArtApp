package com.example.artapp.adapters

import RetroRepository
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.artapp.ActivityForSavedItem
import com.example.artapp.R
import com.example.artapp.data.response.SavedItem
import kotlinx.android.synthetic.main.saved_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SavedAdaptor(val items: MutableList<SavedItem>, val context: Context) :
    RecyclerView.Adapter<SavedAdaptor.SavedViewHolder>() {


    class SavedViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val address_name = view.text_address
        val city_name = view.text_view_title
        val button_to_delete = view.delete_button


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedViewHolder {

        return SavedViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.saved_item, parent, false))

    }

    override fun onBindViewHolder(holder: SavedViewHolder, position: Int) {

        holder?.address_name?.text = items.get(position).address
        holder?.city_name?.text = items.get(position).cityName
        holder?.button_to_delete.setOnClickListener {


            CoroutineScope(Dispatchers.IO).launch {
                val retroRepository = RetroRepository.get()
                retroRepository.deletebyguid(items.get(position).guid)
            }

            notifyDataSetChanged()

        }

        holder?.itemView.setOnClickListener {

            val intent =
                Intent(context, ActivityForSavedItem::class.java)

            intent.putExtra("Pic_link", items.get(position).pictLink)
            intent.putExtra("Geo_lat", items.get(position).geoCoordinates.lat)
            intent.putExtra("Geo_lon", items.get(position).geoCoordinates.lon)
            intent.putExtra("Address", items.get(position).address)
            intent.putExtra("Artist", items.get(position).writersNames)
            intent.putExtra("Street_link", items.get(position).linkOpenStreetMap)
            intent.putExtra("Year", items.get(position).createdYear)
            intent.putExtra("City", items.get(position).cityName)

            context.startActivity(intent)

        }

    }

    override fun getItemCount(): Int {

        return items.size

    }


}


