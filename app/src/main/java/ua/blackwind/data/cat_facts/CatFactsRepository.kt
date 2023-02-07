package ua.blackwind.data.cat_facts

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.json.JSONArray
import ua.blackwind.data.api.CatFactJSON
import ua.blackwind.data.db.CatFactsDatabase
import ua.blackwind.data.db.model.CurrentRandomCatFactId
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

    init {
        GlobalScope.launch(IO) {

            dao.getLastLoadedRandomFactId().collectLatest { lastId ->

                if (lastId == null) {
                    fetchNewRandomCatFacts(20)
                    return@collectLatest
                }

                val currentRandomCatFactId = dao.getCurrentRandomFactId()?.id ?: 1

                if (lastId - currentRandomCatFactId < 20) {
                    fetchNewRandomCatFacts(20)
                }
            }

        }
    }

    override suspend fun getCurrentRandomFactId(): Int {
        return dao.getCurrentRandomFactId()?.id ?: 1
    }

    override suspend fun insertCurrentRandomFactId(id: Int) {
        dao.insertCurrentRandomFactId(CurrentRandomCatFactId(id = id))
    }

    fun getLastRandomFactId(): Flow<Int?> = dao.getLastLoadedRandomFactId()
    override suspend fun getRandomFactsListByIdRange(
        first: Int,
        last: Int
    ): List<RandomCatFactDbModel> {
        return dao.getRandomCatFactsByIdRange(first, last)
    }

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

    fun fetchNewRandomCatFacts(count: Int) {

        catFactsRemoteDataSource.loadNewCatFacts(
            count,
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
            GlobalScope.launch(IO) { fetchNewRandomCatFacts(20) }
        }
    }

    private fun handleApiErrors(exception: Exception) {
        //TODO implement error message channel to inform user about request failures when db is empty
    }

    private suspend fun checkDbRandomCatFactsCount() = dao.getRandomFactsCount()

}