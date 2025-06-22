package co.uk.bbk.culinarycompanion

import androidx.room.*
import co.uk.bbk.culinarycompanion.Recipe
import co.uk.bbk.culinarycompanion.Category


@Dao
interface RecipeDao {

    @Query("SELECT * FROM Recipes")
    suspend fun getAllRecipes(): List<Recipe>

    @Query("SELECT * FROM Recipes WHERE category = :category")
    suspend fun getRecipesByCategory(category: Category): List<Recipe>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: Recipe)

    @Update
    suspend fun update(recipe: Recipe)

    @Delete
    suspend fun delete(recipe: Recipe)

    //used for mock data
    @Query("SELECT COUNT(*) FROM Recipes")
    suspend fun getRecipeCount(): Int

    @Insert
    suspend fun insertAll(recipes: List<Recipe>)

    @Query("SELECT * FROM Recipes")
    suspend fun getAllRecipesList(): List<Recipe>


}
