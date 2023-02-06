package ua.blackwind.ui.model

import ua.blackwind.data.db.model.RandomCatFactDbModel

data class CatFact(
    val id: Int,
    val text: String,
    val imgUrl: String
)

fun RandomCatFactDbModel.toCatFact() =
    CatFact(this.id, this.text, "")
