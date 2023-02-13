package ua.blackwind.data.cat_images

import kotlinx.coroutines.flow.Flow
import ua.blackwind.data.db.model.CatImageDbModel

interface ICatImagesRepository {
    fun getAllCatImagesList(): Flow<List<CatImageDbModel>>
    fun deleteCatImageById(id: Int)
    fun fetchMoreCatImages()
}