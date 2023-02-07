package ua.blackwind.data.cat_facts

import kotlinx.coroutines.flow.Flow
import ua.blackwind.data.db.model.CurrentRandomCatFactId
import ua.blackwind.data.db.model.FavoriteCatFactDBModel
import ua.blackwind.data.db.model.RandomCatFactDbModel

interface ICatFactsRepository {
    fun getAllRandomCatFacts(): Flow<List<RandomCatFactDbModel>>
    fun getAllFavoriteCatFacts(): Flow<List<FavoriteCatFactDBModel>>
    suspend fun deleteRandomCatFactById(id: Int)
    suspend fun deleteFavoriteCatFactById(id: Int)
    suspend fun getRandomFactsListByIdRange(first: Int, last: Int): List<RandomCatFactDbModel>
    suspend fun insertCurrentRandomFactId(id: Int)
    fun getCurrentRandomFactId(): Flow<Int>
    fun getLastRandomFactId(): Flow<Int>
}