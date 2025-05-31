package co.uk.bbk.culinarycompanion

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


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
): Serializable

enum class Category {
    Breakfast,
    Brunch,
    Lunch,
    Dinner,
    Desserts,
    Other
}

