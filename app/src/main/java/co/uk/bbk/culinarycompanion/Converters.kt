package co.uk.bbk.culinarycompanion

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromCategory(category: Category): String {
        return category.name
    }

    @TypeConverter
    fun toCategory(value: String): Category {
        return Category.valueOf(value)
    }
}
