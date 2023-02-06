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

    @Insert
    suspend fun insertFavoriteCatFact(catFactDBModel: FavoriteCatFactDBModel)

    @Insert
    suspend fun insertCatImage(catImage: CatImageDbModel)

    @Query("DELETE FROM facts_random WHERE id == :id")
    suspend fun deleteRandomCatFactById(id: Int)

    @Query("DELETE FROM facts_favorite WHERE id == :id")
    suspend fun deleteFavoriteCatFactById(id: Int)

    @Query("DELETE FROM cat_image WHERE id == :id")
    suspend fun deleteCatImageById(id: Int)

    @Query("SELECT * FROM facts_random ORDER BY id DESC")
    fun getAllRandomCatFacts(): Flow<List<RandomCatFactDbModel>>

    @Query("SELECT * FROM facts_favorite ORDER BY id DESC")
    fun getAllFavoriteCatFacts(): Flow<List<FavoriteCatFactDBModel>>

    @Query("SELECT * FROM cat_image ORDER BY id DESC")
    fun getAllCatImages(): Flow<List<CatImageDbModel>>

    @Query("SELECT COUNT(id) FROM facts_random")
    suspend fun getRandomFactsCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentRandomFactId(id: CurrentRandomCatFactId)

    @Query("SELECT * FROM current_random_fact WHERE id == 1")
    suspend fun getCurrentRandomFactId(): CurrentRandomCatFactId

    @Query("SELECT id FROM facts_random ORDER BY ASC LIMIT 1")
    fun getLastLoadedRandomFactId(): Flow<Int>

    @Query("SELECT * from facts_random WHERE id BETWEEN :first AND :last ORDER BY id ASC ")
    fun getRandomCatFactsByIdRange(first: Int, last: Int): List<RandomCatFactDbModel>
}