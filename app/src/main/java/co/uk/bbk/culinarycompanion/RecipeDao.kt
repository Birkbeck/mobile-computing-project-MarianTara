package co.uk.bbk.culinarycompanion

import androidx.room.*

/**
 * Data Access Object (DAO) for managing Recipe entities in the Room database.
 */
@Dao
interface RecipeDao {

    /**
     * Retrieves all recipes from the database.
     */
    @Query("SELECT * FROM Recipes")
    suspend fun getAllRecipes(): List<Recipe>

    /**
     * Retrieves recipes that belong to the specified category.
     */
    @Query("SELECT * FROM Recipes WHERE category = :category")
    suspend fun getRecipesByCategory(category: Category): List<Recipe>

    /**
     * Inserts a recipe into the database.
     * Replaces existing entry if ID conflicts.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: Recipe)

    /**
     * Updates an existing recipe in the database.
     */
    @Update
    suspend fun update(recipe: Recipe)

    /**
     * Deletes the specified recipe from the database.
     */
    @Delete
    suspend fun delete(recipe: Recipe)

    /**
     * Returns the number of recipes currently stored.
     * Used for mock data population checks.
     */
    @Query("SELECT COUNT(*) FROM Recipes")
    suspend fun getRecipeCount(): Int

    /**
     * Inserts a list of recipes into the database.
     */
    @Insert
    suspend fun insertAll(recipes: List<Recipe>)

    /**
     * Retrieves all recipes as a list.
     */
    @Query("SELECT * FROM Recipes")
    suspend fun getAllRecipesList(): List<Recipe>

    /**
     * Retrieves a specific recipe by its ID.
     */
    @Query("SELECT * FROM Recipes WHERE id = :id")
    suspend fun getRecipeById(id: Int): Recipe?
}
