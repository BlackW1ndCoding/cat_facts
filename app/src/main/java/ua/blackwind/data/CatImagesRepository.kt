package ua.blackwind.data

import android.content.Context
import ua.blackwind.data.db.CatFactsDatabase
import javax.inject.Inject

class CatImagesRepository @Inject constructor(
    private val context: Context,
    db: CatFactsDatabase
) {

}