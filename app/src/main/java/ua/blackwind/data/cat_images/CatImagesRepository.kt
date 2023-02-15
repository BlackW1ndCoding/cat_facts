package ua.blackwind.data.cat_images

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.json.JSONArray
import ua.blackwind.data.api.CatImageJson
import ua.blackwind.data.api.toDbModel
import ua.blackwind.data.db.CatFactsDatabase
import ua.blackwind.data.db.model.CatImageDbModel
import ua.blackwind.di.ApplicationScope
import ua.blackwind.di.IoDispatcher
import javax.inject.Inject

class CatImagesRepository @Inject constructor(
    @ApplicationScope private val applicationScope: CoroutineScope,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val db: CatFactsDatabase,
    private val remoteDataSource: ICatImagesRemoteDataSource,
    private val moshi: Moshi
): ICatImagesRepository {

    init {
        applicationScope.launch(dispatcher) {
            db.dao.getLastLoadedRandomFactId()
                .combine(db.dao.getCurrentRandomFactId()) { last, _ -> last }
                .combine(db.dao.getAllCatImages()) { lastId, imageList ->
                    Pair(lastId, imageList)
                }.collectLatest { (id, list) ->
                    try {
                        Log.d("IMAGE", "Last fact id $id and last cat image ${list.last().id}")
                        if (list.last().id < (id ?: 1)) {
                            fetchMoreCatImages()
                        }
                    } catch (e: NoSuchElementException) {
                        fetchMoreCatImages()
                    }
                }
        }
    }

    override fun getAllCatImagesList(): Flow<List<CatImageDbModel>> {
        return db.dao.getAllCatImages()
    }

    override fun deleteCatImageById(id: Int) {
        TODO("Not yet implemented")
    }

    override fun fetchMoreCatImages() {
        Log.d("IMAGE", "Fetching more images")
        remoteDataSource.loadNewCatImages(
            ::successCallBack,
            ::errorCallBack
        )
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun successCallBack(jsonArray: JSONArray) {
        val adapter = moshi.adapter<List<CatImageJson>>()
        try {
            adapter.fromJson(jsonArray.toString())?.let { jsonList ->
                jsonList.filter { it.url.takeLast(3) != GIF_IMAGE_FILE_EXTENSION }
                    .map { it.toDbModel() }.also { imageList ->
                        applicationScope.launch(dispatcher) { db.dao.insertCatImageList(imageList) }
                    }
            }
        } catch (exception: Exception) {
            fetchMoreCatImages()
        }
    }

    private fun errorCallBack(exception: Exception) {
        Log.d("IMAGES", exception.message ?: "Unknown error")
    }

    companion object {
        private const val GIF_IMAGE_FILE_EXTENSION = "gif"
    }
}