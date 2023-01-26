package ua.blackwind.data

import kotlinx.coroutines.flow.Flow
import ua.blackwind.data.db.model.FavoriteCatFactDBModel
import ua.blackwind.data.db.model.RandomCatFactDbModel

interface ICatFactsRepository {
    fun getAllRandomCatFacts(): Flow<List<RandomCatFactDbModel>>
    fun getAllFavoriteCatFacts(): Flow<List<FavoriteCatFactDBModel>>
    fun deleteRandomCatFactById(id: Int)
    fun deleteFavoriteCatFactById(id: Int)
}