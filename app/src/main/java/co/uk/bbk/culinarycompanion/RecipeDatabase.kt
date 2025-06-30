package co.uk.bbk.culinarycompanion

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * Room database definition for storing Recipe entities.
 * Provides a singleton instance and access to the RecipeDao.
 */
@Database(entities = [Recipe::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RecipeDatabase : RoomDatabase() {

    /**
     * Returns the DAO for recipe operations.
     */
    abstract fun recipeDao(): RecipeDao

    companion object {
        @Volatile private var INSTANCE: RecipeDatabase? = null

        /**
         * Retrieves the singleton instance of the database.
         * Creates it if it does not already exist.
         */
        fun getInstance(context: Context): RecipeDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "recipe_database"
                ).fallbackToDestructiveMigration().build().also { INSTANCE = it }
            }
        }
    }
}
