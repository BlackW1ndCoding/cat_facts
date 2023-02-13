package ua.blackwind.data.cat_images

import org.json.JSONArray

interface ICatImagesRemoteDataSource {
    fun loadNewCatImages(
        successCallback: (JSONArray) -> Unit,
        errorCallback: (Exception) -> Unit
    )
}