package co.uk.bbk.culinarycompanion

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Entity class representing a Recipe stored in the Room database.
 */
@Entity(tableName = "Recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val ingredients: String,
    val instructions: String,
    @field:ColumnInfo(name = "category")
    val category: Category,
    @field:ColumnInfo(name = "imageUri")
    val imageUri: String
) : Serializable

/**
 * Enum representing recipe categories.
 */
enum class Category {
    Breakfast,
    Brunch,
    Lunch,
    Dinner,
    Desserts,
    Other
}
