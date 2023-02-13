package ua.blackwind.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_random_fact")
data class CurrentRandomCatFactId(
    val id: Int,
    @PrimaryKey
    val dbId: Int = 1,
)
