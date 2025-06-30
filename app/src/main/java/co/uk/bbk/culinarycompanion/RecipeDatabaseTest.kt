package co.uk.bbk.culinarycompanion

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Unit tests for RecipeDatabase using a Room database.
 * Verifies insert, retrieval and deletion operations.
 */
@RunWith(AndroidJUnit4::class)
class RecipeDatabaseTest {

    private lateinit var db: RecipeDatabase
    private lateinit var dao: RecipeDao

    private val testRecipe = Recipe(
        id = 1,
        title = "Test Recipe",
        category = Category.Breakfast,
        ingredients = "Eggs, Flour",
        instructions = "Mix and cook",
        imageUri = "drawable/test_image"
    )

    /**
     * Creates a Room database and initialises the DAO before each test.
     */
    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, RecipeDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.recipeDao()
    }

    /**
     * Closes the Room database after each test.
     */
    @After
    @Throws(IOException::class)
    fun closeDb() = db.close()

    /**
     * Tests inserting a recipe and retrieving it from the database.
     */
    @Test
    fun insertAndRetrieveRecipe() = runBlocking {
        dao.insert(testRecipe)
        val recipes = dao.getAllRecipes()
        Assert.assertEquals(1, recipes.size)
        Assert.assertEquals("Test Recipe", recipes[0].title)
    }

    /**
     * Tests retrieving a recipe by its ID.
     */
    @Test
    fun getRecipeById() = runBlocking {
        dao.insert(testRecipe)
        val found = dao.getRecipeById(1)
        Assert.assertNotNull(found)
        Assert.assertEquals("Test Recipe", found?.title)
    }

    /**
     * Tests deleting a recipe and verifying it's removed from the database.
     */
    @Test
    fun deleteRecipe() = runBlocking {
        dao.insert(testRecipe)
        dao.delete(testRecipe)
        val recipes = dao.getAllRecipes()
        Assert.assertTrue(recipes.isEmpty())
    }
}
