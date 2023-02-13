package ua.blackwind.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ua.blackwind.data.db.model.CatImageDbModel
import ua.blackwind.data.db.model.CurrentRandomCatFactId
import ua.blackwind.data.db.model.FavoriteCatFactDBModel
import ua.blackwind.data.db.model.RandomCatFactDbModel

@Dao
interface CatFactsDao {

    @Insert
    suspend fun insertRandomCatFact(catFact: RandomCatFactDbModel)

    @Insert
    suspend fun insertRandomCatFactsList(list: List<RandomCatFactDbModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteCatFact(fact: FavoriteCatFactDBModel)

    @Insert
    suspend fun insertCatImageList(list: List<CatImageDbModel>)

    @Query("DELETE FROM facts_random WHERE id == :id")
    suspend fun deleteRandomCatFactById(id: Int)

    @Query("DELETE FROM facts_favorite WHERE id == :id")
    suspend fun deleteFavoriteCatFactById(id: Int)

    @Query("DELETE FROM cat_image WHERE id == :id")
    suspend fun deleteCatImageById(id: Int)

    @Query("SELECT * FROM facts_random ORDER BY id ASC")
    fun getAllRandomCatFacts(): Flow<List<RandomCatFactDbModel>>

    @Query("SELECT * FROM facts_favorite ORDER BY id ASC")
    fun getAllFavoriteCatFacts(): Flow<List<FavoriteCatFactDBModel>>

    @Query("SELECT * FROM cat_image ORDER BY id DESC")
    fun getAllCatImages(): Flow<List<CatImageDbModel>>

    @Query("SELECT COUNT(id) FROM facts_random")
    suspend fun getRandomFactsCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentRandomFactId(id: CurrentRandomCatFactId)

    @Query("SELECT * FROM current_random_fact WHERE dbId == 1")
    fun getCurrentRandomFactId(): Flow<CurrentRandomCatFactId?>

    @Query("SELECT id FROM facts_random ORDER BY id DESC LIMIT 1")
    fun getLastLoadedRandomFactId(): Flow<Int?>

    @Query("SELECT * FROM facts_random WHERE id BETWEEN :first AND :last ORDER BY id ASC ")
    fun getRandomCatFactsByIdRange(first: Int, last: Int): List<RandomCatFactDbModel>

    @Query("DELETE FROM facts_random WHERE id < :id")
    suspend fun deleteRandomCatFactsWithIdLessThan(id: Int)
}