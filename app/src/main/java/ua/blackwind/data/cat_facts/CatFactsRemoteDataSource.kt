package ua.blackwind.data.cat_facts

import com.android.volley.Request.Method
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import org.json.JSONArray
import javax.inject.Inject

class CatFactsRemoteDataSource @Inject constructor(
    private val requestQueue: RequestQueue
): ICatFactsRemoteDataSource {

    override fun loadNewCatFacts(
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