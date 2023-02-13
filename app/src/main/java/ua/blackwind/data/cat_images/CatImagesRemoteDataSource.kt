package ua.blackwind.data.cat_images

import com.android.volley.Request.Method
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import org.json.JSONArray

class CatImagesRemoteDataSource(private val requestQueue: RequestQueue):
    ICatImagesRemoteDataSource {
    override fun loadNewCatImages(
        successCallback: (JSONArray) -> Unit,
        errorCallback: (Exception) -> Unit
    ) {
        val request = JsonArrayRequest(
            Method.GET,
            IMAGE_BASE_URL + IMAGE_URL_LIMIT_ARGUMENT,
            null,
            successCallback,
            errorCallback
        )
        requestQueue.add(request)
    }

    companion object {
        private const val IMAGE_BASE_URL = "https://api.thecatapi.com/v1/images/search"
        private const val IMAGE_URL_LIMIT_ARGUMENT = "?limit=10"
    }
}