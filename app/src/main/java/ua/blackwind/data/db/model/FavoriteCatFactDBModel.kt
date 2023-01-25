package ua.blackwind.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteCatFactDBModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val text: String,
    val imageUrl: String
)
