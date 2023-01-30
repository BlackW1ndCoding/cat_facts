package ua.blackwind.data

import android.content.Context
import kotlinx.coroutines.flow.Flow
import ua.blackwind.data.db.CatFactsDatabase
import ua.blackwind.data.db.model.CatImageDbModel
import javax.inject.Inject

class CatImagesRepository @Inject constructor(
    private val context: Context,
    db: CatFactsDatabase
): ICatImageRepository {
    override fun getAllCatImagesList(): Flow<List<CatImageDbModel>> {
        TODO("Not yet implemented")
    }

    override fun deleteCatImageById(id: Int) {
        TODO("Not yet implemented")
    }

}