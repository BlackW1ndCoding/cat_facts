package ua.blackwind.data.api

import com.squareup.moshi.Json
import ua.blackwind.data.db.model.CatImageDbModel

data class CatImageJson(
    @Json(name = "url")
    val url: String,
    @Json(name = "width")
    val width: Int,
    @Json(name = "height")
    val height: Int
)

fun CatImageJson.toDbModel() =
    CatImageDbModel(
        0,
        this.url,
        this.width,
        this.height
    )
