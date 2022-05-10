import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.artapp.data.response.ArtItem
import com.example.artapp.data.response.GeoCoordinates
import com.example.artapp.data.response.SavedItem
import com.example.artapp.toroom.AppDatabase
import java.util.concurrent.Executors


private const val DATABASE_NAME = "retrodatabase.db"

class RetroRepository private constructor(context: Context) {


    private val database: AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        DATABASE_NAME
    ).createFromAsset("databases/retrodatabase.db").build()


    private val retroDao = database.getAppDao()

    private val executor = Executors.newSingleThreadExecutor()


    fun getArtItems(): LiveData<List<ArtItem>> = retroDao.getAllRecords()

    fun getArtItem(geoCoordinates: GeoCoordinates): LiveData<ArtItem?> =
        retroDao.getArtItem(geoCoordinates)

    fun getArtItemsByCity(city: String): LiveData<List<ArtItem>> = retroDao.getArtItemsByCity(city)

    fun updateArtItem(item: ArtItem) {
        executor.execute {
            retroDao.updateItems(item)
        }
    }

    fun addArtItem(item: ArtItem) {
        retroDao.addItem(item)
    }

    fun deleteItems() = retroDao.deleteAllRecords()

    private val saveDao = database.getSavedDao()

    fun getSavedItems(): LiveData<List<SavedItem>> = saveDao.getAllRecords()

    fun getSaveItem(geoCoordinates: GeoCoordinates): LiveData<SavedItem?> =
        saveDao.getArtItem(geoCoordinates)

    fun updateSaveItem(item: SavedItem) {
        executor.execute {
            saveDao.updateItems(item)
        }
    }

    fun addSaveItem(item: SavedItem) {
        saveDao.addItem(item)
    }


    fun addArtItemtoSaved(item: ArtItem) {
        saveDao.addArtItemtoSaved(item)
    }

    fun deleteSaveItems() = saveDao.deleteAllRecords()

    fun deletebyguid(guid: String) = saveDao.deleteByGuId(guid)

    companion object {
        private var INSTANCE: RetroRepository? = null


        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = RetroRepository(context)
            }
        }


        fun get(): RetroRepository {
            return INSTANCE ?: throw IllegalStateException("RetroRepository must be initialized")

        }


    }
}