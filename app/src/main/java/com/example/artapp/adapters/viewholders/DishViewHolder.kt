package dev.euryperez.nested_recyclerview.adapters.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.artapp.R
import dev.euryperez.nested_recyclerview.models.Dish
import dev.euryperez.nested_recyclerview.models.RailsClickEvent

class DishViewHolder(itemView: View, val onItemClicked: (RailsClickEvent) -> Unit) :
    RecyclerView.ViewHolder(itemView) {

    private val ivDish: ImageView = itemView.findViewById(R.id.iv_dish)
    private val tvDish: TextView = itemView.findViewById(R.id.tv_dish)

    fun bind(dish: Dish) {
        tvDish.text = dish.name

        Glide.with(itemView)
            .load(dish.HiddenUrl)
            .into(ivDish)

        city_icon_put(tvDish.text.toString())






        itemView.setOnClickListener {
            onItemClicked(RailsClickEvent.DishItemEvent(dish))
        }
    }


    private fun city_icon_put(city_chosen: String) {

        when (city_chosen) {


            // Germany
            "Berlin" -> Glide.with(itemView).load(R.drawable.berlin_1).into(ivDish)
            "Aachen" -> Glide.with(itemView).load(R.drawable.aachen).into(ivDish)
            "Essen" -> Glide.with(itemView).load(R.drawable.essen).into(ivDish)
            "Mannheim" -> Glide.with(itemView).load(R.drawable.mangheim).into(ivDish)
            "Dortmund" -> Glide.with(itemView).load(R.drawable.dortmund).into(ivDish)

            //France
            "Montpellier" -> Glide.with(itemView).load(R.drawable.montpellier).into(ivDish)
            "Strasbourg" -> Glide.with(itemView).load(R.drawable.strasbourg).into(ivDish)
            "Bordeaux" -> Glide.with(itemView).load(R.drawable.bordeaux).into(ivDish)
            "Toulouse" -> Glide.with(itemView).load(R.drawable.tolouse).into(ivDish)

            //Belgium
            "Gent" -> Glide.with(itemView).load(R.drawable.gent).into(ivDish)
            "Oostende" -> Glide.with(itemView).load(R.drawable.ostende).into(ivDish)
            "Antwerpen" -> Glide.with(itemView).load(R.drawable.antwerpen).into(ivDish)
            "Leuven" -> Glide.with(itemView).load(R.drawable.leuven).into(ivDish)

            //Norway
            "Stavanger" -> Glide.with(itemView).load(R.drawable.stavanger).into(ivDish)
            "Oslo" -> Glide.with(itemView).load(R.drawable.oslo).into(ivDish)
            "Bergen" -> Glide.with(itemView).load(R.drawable.bergen).into(ivDish)

            //Spain
            "Valencia" -> Glide.with(itemView).load(R.drawable.valencia).into(ivDish)
            "Vigo" -> Glide.with(itemView).load(R.drawable.vigo_1).into(ivDish)

            //Netherlands
            "Leeuwarden" -> Glide.with(itemView).load(R.drawable.leeuvarden).into(ivDish)
            "Amsterdam" -> Glide.with(itemView).load(R.drawable.amsterdan).into(ivDish)
            "Arnhem" -> Glide.with(itemView).load(R.drawable.arnhem).into(ivDish)
            "Rotterdam" -> Glide.with(itemView).load(R.drawable.rotterdam).into(ivDish)
            "Eindhoven" -> Glide.with(itemView).load(R.drawable.eindhoven).into(ivDish)

            //Estonia
            "Tallinn" -> Glide.with(itemView).load(R.drawable.tallin).into(ivDish)

            //Italy
            "Padova" -> Glide.with(itemView).load(R.drawable.padova).into(ivDish)
            "Milano" -> Glide.with(itemView).load(R.drawable.milan).into(ivDish)

        }
    }


}