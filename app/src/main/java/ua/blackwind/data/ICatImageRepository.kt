package ua.blackwind.data

import kotlinx.coroutines.flow.Flow
import ua.blackwind.data.db.model.CatImageDbModel

interface ICatImageRepository {
    fun getAllCatImagesList(): Flow<List<CatImageDbModel>>
    fun deleteCatImageById(id: Int)
    fun fetchMoreCatImages()
}