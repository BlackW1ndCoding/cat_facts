package ua.blackwind.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "facts_random")
data class RandomCatFactDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val text: String
)
