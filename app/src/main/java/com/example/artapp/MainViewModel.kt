package dev.euryperez.nested_recyclerview

import RetroRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.artapp.data.response.ArtItem
import dev.euryperez.nested_recyclerview.models.Category
import dev.euryperez.nested_recyclerview.models.Dish
import dev.euryperez.nested_recyclerview.models.Menu

class MainViewModel : ViewModel() {

    private val _menuLiveData: MutableLiveData<Menu> = MutableLiveData()
    val menuLiveData:LiveData<Menu> = _menuLiveData
    private val retroRepository = RetroRepository.get()


    init {
        val Germanlist = listOf(
            Dish("Berlin", "https://images.unsplash.com/photo-1582234372722-50d7ccc30ebd?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1000&q=80"),
            Dish("Aachen", "https://images.unsplash.com/photo-1582170090097-b251ddbbf7f3?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=914&q=80"),
            Dish("Essen", "https://images.unsplash.com/photo-1585297099535-d5f84e833797?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1000&q=80"),
            Dish("Mannheim", "https://images.unsplash.com/photo-1582169296194-e4d644c48063?ixlib=rb-1.2.1&auto=format&fit=crop&w=1203&q=80"),
            Dish("Dortmund", "https://images.unsplash.com/photo-1534352956036-cd81e27dd615?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=645&q=80")
        )

        val Francelist = listOf(
            Dish("Montpellier", "https://images.unsplash.com/photo-1473093295043-cdd812d0e601?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80"),
            Dish("Strasbourg", "https://images.unsplash.com/photo-1551892269-860b1e482f98?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80"),
            Dish("Bordeaux", "https://images.unsplash.com/photo-1513104890138-7c749659a591?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80"),
            Dish("Toulouse", "https://images.unsplash.com/photo-1541283014184-791fa57c0735?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80")
        )

        val Belgiumlist = listOf(
            Dish("Gent", "https://images.unsplash.com/photo-1548811256-1627d99e7a2c?ixlib=rb-1.2.1&auto=format&fit=crop&w=1000&q=80"),
            Dish("Oostende", "https://images.unsplash.com/photo-1555126634-323283e090fa?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=700&q=80"),
            Dish("Antwerpen", "https://images.unsplash.com/photo-1525755662778-989d0524087e?ixlib=rb-1.2.1&auto=format&fit=crop&w=1267&q=80"),
            Dish("Leuven", "https://images.unsplash.com/photo-1503764654157-72d979d9af2f?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1353&q=80")
        )

        val Norwaylist = listOf(
            Dish("Oslo", "https://images.unsplash.com/photo-1509482560494-4126f8225994?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1400&q=80")
        )

        val Spainlist = listOf(
            Dish("Valencia", "https://images.unsplash.com/photo-1565733618599-cb82f14f34ac?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=675&q=80"),
            Dish("Vigo", "https://images.unsplash.com/photo-1570275239925-4af0aa93a0dc?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1351&q=80")
        )

        val Netherlandslist = listOf(
            Dish("Leeuwarden", "https://www.unacolombianaencalifornia.com/wp-content/uploads/2012/08/bandeja_paisa-1-1-e1466957278785.jpg"),
            Dish("Amsterdam", "https://t2.rg.ltmcdn.com/es/images/1/4/4/img_bunuelos_colombianos_31441_600.jpg"),
            Dish("Arnhem", "https://cdn.colombia.com/sdi/2012/03/05/patacones-especiales-511773.jpg"),
            Dish("Rotterdam", "https://www.vvsupremo.com/wp-content/uploads/2015/11/900X570_Pandebono-Columbian-Cheese-Bread.jpg"),
            Dish("Eindhoven", "https://www.vvsupremo.com/wp-content/uploads/2015/11/900X570_Pandebono-Columbian-Cheese-Bread.jpg")
        )

        val Estonialist = listOf(
            Dish("Tallinn", "https://www.unacolombianaencalifornia.com/wp-content/uploads/2012/08/bandeja_paisa-1-1-e1466957278785.jpg")
        )

        val Italylist = listOf(
            Dish("Padova", "https://www.unacolombianaencalifornia.com/wp-content/uploads/2012/08/bandeja_paisa-1-1-e1466957278785.jpg"),
            Dish("Milano", "https://t2.rg.ltmcdn.com/es/images/1/4/4/img_bunuelos_colombianos_31441_600.jpg")
        )

        val categories = listOf(
            Category("Germany", Germanlist),
            Category("France", Francelist),
            Category("Belgium", Belgiumlist),
            Category("Norway", Norwaylist),
            Category("Spain", Spainlist),
            Category("Netherlands", Netherlandslist),
            Category("Estonia", Estonialist),
            Category("Italy", Italylist)
        )

        _menuLiveData.value = Menu(categories)
    }

    fun insertArtItem(item: ArtItem){
        retroRepository.addArtItem(item)
    }


}