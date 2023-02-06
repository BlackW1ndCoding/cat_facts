package ua.blackwind.data.cat_facts

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.json.JSONArray
import ua.blackwind.data.api.CatFactJSON
import ua.blackwind.data.db.CatFactsDatabase
import ua.blackwind.data.db.model.FavoriteCatFactDBModel
import ua.blackwind.data.db.model.RandomCatFactDbModel
import ua.blackwind.data.mapToDbModel
import javax.inject.Inject

class CatFactsRepository @Inject constructor(
    private val catFactsRemoteDataSource: ICatFactsRemoteDataSource,
    db: CatFactsDatabase,
    private val moshi: Moshi
): ICatFactsRepository {
    private val dao = db.dao
    override fun getAllRandomCatFacts(): Flow<List<RandomCatFactDbModel>> =
        dao.getAllRandomCatFacts()

    override fun getAllFavoriteCatFacts(): Flow<List<FavoriteCatFactDBModel>> =
        dao.getAllFavoriteCatFacts()

    override suspend fun deleteRandomCatFactById(id: Int) {
        dao.deleteRandomCatFactById(id)
    }

    override suspend fun deleteFavoriteCatFactById(id: Int) {
        dao.deleteRandomCatFactById(id)
    }

    suspend fun fetchNewRandomCatFacts() {
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
        try {
            adapter.fromJson(json.toString())?.let { jsonList ->
                jsonList.filter { it.status.verified ?: false }
                    .map { catFactJSON -> catFactJSON.mapToDbModel() }
            }?.let {
                GlobalScope.launch(IO) { dao.insertRandomCatFactsList(it) }
            }
        } catch (exception: Exception) {
            GlobalScope.launch(IO) { fetchNewRandomCatFacts() }
        }
    }

    private fun handleApiErrors(exception: Exception) {
        //TODO implement error message channel to inform user about request failures when db is empty
    }

    private suspend fun checkDbRandomCatFactsCount() = dao.getRandomFactsCount()

}