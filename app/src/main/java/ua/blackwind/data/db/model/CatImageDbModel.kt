package ua.blackwind.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CatImageDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val url: String
)