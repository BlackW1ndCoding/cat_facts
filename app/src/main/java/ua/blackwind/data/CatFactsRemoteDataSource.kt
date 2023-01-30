package ua.blackwind.data

import android.content.Context
import com.android.volley.Request.Method
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONArray
import javax.inject.Inject

class CatFactsRemoteDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val requestQueue = Volley.newRequestQueue(context)

    fun loadNewCatFacts(
        factsRequestCount: Int,
        successCallback: (JSONArray) -> Unit,
        errorCallback: (Exception) -> Unit
    ) {
        val request = JsonArrayRequest(
            Method.GET,
            REQUEST_URL + factsRequestCount,
            null,
            successCallback,
            errorCallback
        )
        requestQueue.add(request)
    }

    companion object {
        private const val REQUEST_URL =
            "https://cat-fact.herokuapp.com/random?animal_type=cat&amount="
    }
}