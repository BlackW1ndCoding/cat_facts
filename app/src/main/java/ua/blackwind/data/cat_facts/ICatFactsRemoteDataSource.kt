package ua.blackwind.data.cat_facts

import org.json.JSONArray

interface ICatFactsRemoteDataSource {
    fun loadNewCatFacts(
        factsRequestCount: Int,
        successCallback: (JSONArray) -> Unit,
        errorCallback: (Exception) -> Unit
    )
}