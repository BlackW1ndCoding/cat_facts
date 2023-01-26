package ua.blackwind.data

import android.content.Context
import ua.blackwind.data.db.CatFactsDatabase
import javax.inject.Inject

class CatFactsRepository @Inject constructor(
    private val context: Context,
    db: CatFactsDatabase
) {
    private val dao = db.dao


}