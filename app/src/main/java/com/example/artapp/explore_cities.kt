package com.example.artapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_explore_cities.*


class explore_cities : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_explore_cities, container, false)
    }


    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        explore_next_button.setOnClickListener {

            requireActivity().supportFragmentManager?.beginTransaction()
                .replace(R.id.on_boarding_place_holder, saving_data_boarding())
                .addToBackStack("first")
                .commit()

        }

    }


}