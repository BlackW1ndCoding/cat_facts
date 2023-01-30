package ua.blackwind.data.api

import com.squareup.moshi.Json

data class CatFactJSON(
    @Json(name = "status")
    val status: CatFactStatusJSON,
    @Json(name = "text")
    val text: String
)