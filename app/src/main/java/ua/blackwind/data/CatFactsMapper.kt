package ua.blackwind.data

import ua.blackwind.data.api.CatFactJSON
import ua.blackwind.data.db.model.RandomCatFactDbModel

fun CatFactJSON.mapToDbModel() =
    RandomCatFactDbModel(
        id = 0,
        text = text
    )