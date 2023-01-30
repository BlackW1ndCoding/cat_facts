package ua.blackwind.data

import android.content.Context
import android.util.Log
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.json.JSONArray
import ua.blackwind.data.api.CatFactsListJSON
import ua.blackwind.data.db.CatFactsDatabase
import ua.blackwind.data.db.model.FavoriteCatFactDBModel
import ua.blackwind.data.db.model.RandomCatFactDbModel
import javax.inject.Inject

class CatFactsRepository @Inject constructor(
    private val context: Context,
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
            ::loadFactsIntoDb,
            ::handleApiErrors
        )
    }

    private fun loadFactsIntoDb(json: JSONArray) {
        val adapter: JsonAdapter<CatFactsListJSON> = moshi.adapter(CatFactsListJSON::class.java)
        adapter.fromJsonValue(json)?.let { jsonList ->
            jsonList.list.filter { it.status.verified ?: false }
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