package ua.blackwind.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ua.blackwind.data.db.model.CatImageDbModel
import ua.blackwind.data.db.model.CurrentRandomCatFactId
import ua.blackwind.data.db.model.FavoriteCatFactDBModel
import ua.blackwind.data.db.model.RandomCatFactDbModel

@Database(
    entities = [CatImageDbModel::class,
        FavoriteCatFactDBModel::class,
        RandomCatFactDbModel::class,
        CurrentRandomCatFactId::class],
    version = 1,
    exportSchema = false
)
abstract class CatFactsDatabase: RoomDatabase() {
    abstract val dao: CatFactsDao

    companion object {
        const val DB_NAME = "cat_facts.db"
    }
}