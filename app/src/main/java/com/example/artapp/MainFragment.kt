package dev.euryperez.nested_recyclerview

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.artapp.MapsActivity
import com.example.artapp.R
import com.example.artapp.inflate
import dev.euryperez.nested_recyclerview.adapters.RailsAdapter
import dev.euryperez.nested_recyclerview.models.RailsClickEvent
import dev.euryperez.nested_recyclerview.models.RailsItem

class MainFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()
    private val adapter = RailsAdapter(::onItemClicked)
    private val TAG = "myLogs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel.menuLiveData.observe(::getLifecycle) {
            val railsItems = mutableListOf<RailsItem>()

            it.categories.onEach { category ->
                railsItems.add(RailsItem.RailsTitle(category.title))
                railsItems.add(RailsItem.RailsList(category.dishes))
            }

            adapter.items = railsItems
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = container?.inflate(R.layout.fragment_main)
        val recyclerView = view?.findViewById<RecyclerView>(R.id.rv_rails)
        recyclerView?.adapter = adapter
        return view
    }


    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(
            view,
            savedInstanceState
        )

    }


    private fun onItemClicked(railsClickEvent: RailsClickEvent) {
        when (railsClickEvent) {


            is RailsClickEvent.DishItemEvent -> {

                if (isOnline(requireContext())) {
                    val dish = railsClickEvent.dish
                    val intent = Intent(getActivity(), MapsActivity::class.java)
                    intent.putExtra(MapsActivity.NAME, dish.name)
                    getActivity()?.startActivity(intent)

                } else {
                    Toast.makeText(getActivity(),
                        "Please check your Internet connection",
                        Toast.LENGTH_SHORT).show();
                }

            }
        }
    }


    fun isOnline(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val n = cm.activeNetwork
            if (n != null) {
                val nc = cm.getNetworkCapabilities(n)
                return nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(
                    NetworkCapabilities.TRANSPORT_WIFI)
            }
            return false
        } else {
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnectedOrConnecting
        }
    }




}
