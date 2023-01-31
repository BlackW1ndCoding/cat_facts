package ua.blackwind.data

import android.util.Log
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.json.JSONArray
import ua.blackwind.data.api.CatFactJSON
import ua.blackwind.data.api.CatFactsListJSON
import ua.blackwind.data.db.CatFactsDatabase
import ua.blackwind.data.db.model.FavoriteCatFactDBModel
import ua.blackwind.data.db.model.RandomCatFactDbModel
import javax.inject.Inject

class CatFactsRepository @Inject constructor(
    private val catFactsRemoteDataSource: CatFactsRemoteDataSource,
    db: CatFactsDatabase,
    private val moshi: Moshi
): ICatFactsRepository {
    private val dao = db.dao
    override fun getAllRandomCatFacts(): Flow<List<RandomCatFactDbModel>> =
        dao.getAllRandomCatFacts()

    override fun getAllFavoriteCatFacts(): Flow<List<FavoriteCatFactDBModel>> =
        dao.getAllFavoriteCatFacts()

    override suspend fun deleteRandomCatFactById(id: Int?) {
        dao.deleteRandomCatFactById(id)
    }

    override suspend fun deleteFavoriteCatFactById(id: Int?) {
        dao.deleteRandomCatFactById(id)
    }

    fun fetchNewRandomCatFacts() {
        val factsRequestCount = when (checkDbRandomCatFactsCount()) {
            in 10..20 -> 10
            in 0..10 -> 20
            else -> 0
        }

        if (factsRequestCount == 0) return

        catFactsRemoteDataSource.loadNewCatFacts(
            factsRequestCount,
            ::loadRandomFactsIntoDb,
            ::handleApiErrors
        )
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun loadRandomFactsIntoDb(json: JSONArray) {
        val adapter = moshi.adapter<List<CatFactJSON>>()
        adapter.fromJson(json.toString())?.let { jsonList ->
            jsonList.filter { it.status.verified ?: false }
                .map { catFactJSON -> catFactJSON.mapToDbModel() }
        }?.let {
            GlobalScope.launch(Dispatchers.IO) { dao.insertRandomCatFactsList(it) }
        }
    }

    private fun handleApiErrors(exception: java.lang.Exception) {
        Log.d("JSON_Error", exception.message ?: "Unknown Error")
    }

    private fun checkDbRandomCatFactsCount() = dao.getRandomFactsCount()

}