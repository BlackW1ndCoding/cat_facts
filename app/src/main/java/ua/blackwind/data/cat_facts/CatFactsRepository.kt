package ua.blackwind.data.cat_facts

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import org.json.JSONArray
import ua.blackwind.data.api.ApiState
import ua.blackwind.data.api.CatFactJSON
import ua.blackwind.data.db.CatFactsDatabase
import ua.blackwind.data.db.model.CurrentRandomCatFactId
import ua.blackwind.data.db.model.FavoriteCatFactDBModel
import ua.blackwind.data.db.model.RandomCatFactDbModel
import ua.blackwind.data.mapToDbModel
import ua.blackwind.di.ApplicationScope
import ua.blackwind.di.IoDispatcher
import javax.inject.Inject

class CatFactsRepository @Inject constructor(
    @ApplicationScope private val applicationScope: CoroutineScope,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val catFactsRemoteDataSource: ICatFactsRemoteDataSource,
    db: CatFactsDatabase,
    private val moshi: Moshi
): ICatFactsRepository {
    private val dao = db.dao
    val state = Channel<ApiState>()

    init {
        applicationScope.launch(dispatcher) {
            getCurrentRandomFactId()
                .combine(getLastRandomFactId()) { first, last -> Pair(first, last) }
                .collectLatest { (current, last) ->
                    if (last - current < RANDOM_FACTS_LOAD_SIZE) {
                        fetchNewRandomCatFacts(RANDOM_FACTS_LOAD_SIZE)
                    } else {
                        state.send(ApiState.Success)
                    }

                    if (current % DB_CLEAN_UP_THRESHOLD == 0) {
                        dao.deleteRandomCatFactsWithIdLessThan(current)
                    }
                }
        }
    }

    override fun getCurrentRandomFactId(): Flow<Int> {
        return dao.getCurrentRandomFactId().map { it?.id ?: DEFAULT_ID }
    }

    override suspend fun insertCurrentRandomFactId(id: Int) {
        dao.insertCurrentRandomFactId(CurrentRandomCatFactId(id = id))
    }

    override fun getLastRandomFactId(): Flow<Int> =
        dao.getLastLoadedRandomFactId().map { it ?: DEFAULT_ID }

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
        applicationScope.launch(dispatcher) { state.send(ApiState.Loading) }

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
                applicationScope.launch {
                    dao.insertRandomCatFactsList(it)
                    state.send(ApiState.Success)
                }
            }

        } catch (exception: Exception) {
            applicationScope.launch(dispatcher) { fetchNewRandomCatFacts(RANDOM_FACTS_LOAD_SIZE) }
        }
    }

    private fun handleApiErrors(exception: Exception) {
        applicationScope.launch { state.send(ApiState.Error) }
    }

    companion object {
        private const val RANDOM_FACTS_LOAD_SIZE = 20
        private const val DEFAULT_ID = 1
        private const val DB_CLEAN_UP_THRESHOLD = 30
    }

}