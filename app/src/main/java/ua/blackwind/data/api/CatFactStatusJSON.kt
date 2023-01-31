package ua.blackwind.data.api

import com.squareup.moshi.Json

@Json(name="status")
data class CatFactStatusJSON(
    @Json(name="verified")
    val verified: Boolean?
)
