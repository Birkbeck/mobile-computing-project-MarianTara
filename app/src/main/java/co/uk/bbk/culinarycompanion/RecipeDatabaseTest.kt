package co.uk.bbk.culinarycompanion

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import java.io.IOException

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

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, RecipeDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.recipeDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() = db.close()

    @Test
    fun insertAndRetrieveRecipe() = runBlocking {
        dao.insert(testRecipe)
        val recipes = dao.getAllRecipes()
        Assert.assertEquals(1, recipes.size)
        Assert.assertEquals("Test Recipe", recipes[0].title)
    }

    @Test
    fun getRecipeById() = runBlocking {
        dao.insert(testRecipe)
        val found = dao.getRecipeById(1)
        Assert.assertNotNull(found)
        Assert.assertEquals("Test Recipe", found?.title)
    }

    @Test
    fun deleteRecipe() = runBlocking {
        dao.insert(testRecipe)
        dao.delete(testRecipe)
        val recipes = dao.getAllRecipes()
        Assert.assertTrue(recipes.isEmpty())
    }
}
