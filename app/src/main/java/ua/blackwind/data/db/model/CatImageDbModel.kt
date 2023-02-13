package ua.blackwind.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cat_image")
data class CatImageDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val url: String,
    val width: Int,
    val height: Int
)