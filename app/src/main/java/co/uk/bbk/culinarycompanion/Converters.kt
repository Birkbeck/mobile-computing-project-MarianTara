package co.uk.bbk.culinarycompanion

import androidx.room.TypeConverter

/**
 * Type converters for Room database to handle custom enum types.
 */
class Converters {

    /**
     * Converts a Category enum to a String for Room storage.
     */
    @TypeConverter
    fun fromCategory(category: Category): String {
        return category.name
    }

    /**
     * Converts a String back to a Category enum.
     */
    @TypeConverter
    fun toCategory(value: String): Category {
        return Category.valueOf(value)
    }
}
