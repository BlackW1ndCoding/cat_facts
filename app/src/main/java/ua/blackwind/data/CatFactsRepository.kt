package ua.blackwind.data

import android.content.Context
import ua.blackwind.data.db.CatFactsDatabase
import javax.inject.Inject

class CatFactsRepository @Inject constructor(
    private val context: Context,
    private val db: CatFactsDatabase
) {
}