package co.uk.bbk.culinarycompanion

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ViewModel for populating the database with mock recipes on first launch.
 * Used to pre-fill the app with sample data across all categories.
 * In real world this app will use a database and this will not be used
 * All data passed in the database is from Google and AI generated, All code is mine
 */
class MockDataViewModel(application: Application) : AndroidViewModel(application) {
    private val db = RecipeDatabase.getInstance(application)
    private val dao = db.recipeDao()

    /**
     * Populates the Room database with predefined recipes if it is empty.
     */
    fun populateDatabaseIfEmpty() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentRecipes = dao.getAllRecipesList()
            if (currentRecipes.isEmpty()) {
                val mockRecipes = listOf(
                    //Breakfast
                    Recipe(
                        title = "Blueberry Pancakes",
                        ingredients = "Flour, Eggs, Milk, Blueberries, Sugar, Baking Powder",
                        instructions = "Whisk dry & wet separately → combine → fold berries → fry 2 min/side.",
                        category = Category.Breakfast,
                        imageUri = "android.resource://${getApplication<Application>().packageName}/drawable/breakfast_blueberry_pancakes"
                    ),
                    Recipe(
                        title = "Avocado Toast",
                        ingredients = "Sourdough, Avocado, Lemon Juice, Chili Flakes, Salt",
                        instructions = "Toast bread → mash avocado with lemon & salt → spread → top with chili.",
                        category = Category.Breakfast,
                        imageUri = "android.resource://${getApplication<Application>().packageName}/drawable/breakfast_avocado_toast"
                    ),
                    Recipe(
                        title = "Greek Yogurt Parfait",
                        ingredients = "Greek Yogurt, Honey, Granola, Strawberries, Blueberries",
                        instructions = "Layer yogurt, fruit, granola, repeat; drizzle honey on top.",
                        category = Category.Breakfast,
                        imageUri = "android.resource://${getApplication<Application>().packageName}/drawable/breakfast_parfait"
                    ),
                    Recipe(
                        title = "Classic Omelette",
                        ingredients = "Eggs, Butter, Cheddar, Chives, Salt, Pepper",
                        instructions = "Beat eggs → melt butter → cook eggs 30 s → add cheese → fold & serve.",
                        category = Category.Breakfast,
                        imageUri = "android.resource://${getApplication<Application>().packageName}/drawable/breakfast_omelette"
                    ),

                    // Brunch
                    Recipe(
                        title = "Eggs Benedict",
                        ingredients = "English Muffins, Poached Eggs, Ham, Hollandaise Sauce",
                        instructions = "Toast muffins → warm ham → poach eggs → assemble → spoon sauce.",
                        category = Category.Brunch,
                        imageUri = "android.resource://${getApplication<Application>().packageName}/drawable/brunch_eggs_benedict"
                    ),
                    Recipe(
                        title = "Smoked Salmon Bagel",
                        ingredients = "Bagel, Cream Cheese, Smoked Salmon, Capers, Red Onion",
                        instructions = "Toast bagel → spread cheese → layer salmon, onion, capers.",
                        category = Category.Brunch,
                        imageUri = "android.resource://${getApplication<Application>().packageName}/drawable/brunch_salmon_bagel"
                    ),
                    Recipe(
                        title = "Shakshuka",
                        ingredients = "Tomatoes, Bell Pepper, Onions, Eggs, Cumin, Paprika",
                        instructions = "Simmer veg & spices → crack eggs → cover & cook 6 min → serve with pita.",
                        category = Category.Brunch,
                        imageUri = "android.resource://${getApplication<Application>().packageName}/drawable/brunch_shakshuka"
                    ),
                    Recipe(
                        title = "Banana-Nut French Toast",
                        ingredients = "Bread, Eggs, Milk, Banana, Pecans, Maple Syrup",
                        instructions = "Dip bread → fry → top with caramelized bananas & pecans.",
                        category = Category.Brunch,
                        imageUri = "android.resource://${getApplication<Application>().packageName}/drawable/brunch_french_toast"
                    ),

                    //Lunch
                    Recipe(
                        title = "Caesar Salad",
                        ingredients = "Romaine, Croutons, Parmesan, Caesar Dressing, Chicken (opt.)",
                        instructions = "Chop lettuce → toss with dressing → add toppings.",
                        category = Category.Lunch,
                        imageUri = "android.resource://${getApplication<Application>().packageName}/drawable/lunch_caesar_salad"
                    ),
                    Recipe(
                        title = "Caprese Sandwich",
                        ingredients = "Ciabatta, Mozzarella, Tomato, Basil, Balsamic Glaze",
                        instructions = "Layer cheese & tomato → season → drizzle glaze → grill lightly.",
                        category = Category.Lunch,
                        imageUri = "android.resource://${getApplication<Application>().packageName}/drawable/lunch_caprese_sandwich"
                    ),
                    Recipe(
                        title = "Veggie Burrito Bowl",
                        ingredients = "Rice, Black Beans, Corn, Pico de Gallo, Avocado, Lime",
                        instructions = "Fill bowl with rice → arrange toppings → squeeze lime.",
                        category = Category.Lunch,
                        imageUri = "android.resource://${getApplication<Application>().packageName}/drawable/lunch_burrito_bowl"
                    ),
                    Recipe(
                        title = "Spaghetti Bolognese",
                        ingredients = "Spaghetti, Ground Beef, Tomato Sauce, Onion, Garlic, Herbs",
                        instructions = "Brown beef → add sauce & herbs → simmer 20 min → toss with pasta.",
                        category = Category.Lunch,
                        imageUri = "android.resource://${getApplication<Application>().packageName}/drawable/lunch_spaghetti"
                    ),

                    //Dinner
                    Recipe(
                        title = "Grilled Salmon with Asparagus",
                        ingredients = "Salmon Fillets, Asparagus, Olive Oil, Lemon, Dill",
                        instructions = "Season → grill 4 min/side → serve with roasted asparagus & lemon.",
                        category = Category.Dinner,
                        imageUri = "android.resource://${getApplication<Application>().packageName}/drawable/dinner_salmon"
                    ),
                    Recipe(
                        title = "Chicken Stir-Fry",
                        ingredients = "Chicken, Broccoli, Bell Pepper, Soy Sauce, Ginger, Garlic",
                        instructions = "Sauté chicken → add veg & sauce → cook until crisp-tender.",
                        category = Category.Dinner,
                        imageUri = "android.resource://${getApplication<Application>().packageName}/drawable/dinner_stirfry"
                    ),
                    Recipe(
                        title = "Beef Tacos",
                        ingredients = "Taco Shells, Ground Beef, Lettuce, Cheese, Salsa",
                        instructions = "Cook beef with seasoning → fill shells → add toppings.",
                        category = Category.Dinner,
                        imageUri = "android.resource://${getApplication<Application>().packageName}/drawable/dinner_tacos"
                    ),
                    Recipe(
                        title = "Vegetable Curry",
                        ingredients = "Mixed Veg, Coconut Milk, Curry Paste, Chickpeas, Cilantro",
                        instructions = "Sauté curry paste → add veg & milk → simmer 15 min.",
                        category = Category.Dinner,
                        imageUri = "android.resource://${getApplication<Application>().packageName}/drawable/dinner_curry"
                    ),

                    //Desserts
                    Recipe(
                        title = "Chocolate Lava Cake",
                        ingredients = "Dark Chocolate, Butter, Eggs, Sugar, Flour",
                        instructions = "Mix batter → bake 12 min → center stays gooey.",
                        category = Category.Desserts,
                        imageUri = "android.resource://${getApplication<Application>().packageName}/drawable/dessert_lava_cake"
                    ),
                    Recipe(
                        title = "Strawberry Cheesecake",
                        ingredients = "Cream Cheese, Strawberries, Graham Crust, Sugar, Eggs",
                        instructions = "Blend filling → bake → chill → top with berries.",
                        category = Category.Desserts,
                        imageUri = "android.resource://${getApplication<Application>().packageName}/drawable/dessert_cheesecake"
                    ),
                    Recipe(
                        title = "Tiramisu",
                        ingredients = "Ladyfingers, Mascarpone, Espresso, Cocoa Powder, Rum",
                        instructions = "Dip cookies → layer with cream → chill 4 h → dust cocoa.",
                        category = Category.Desserts,
                        imageUri = "android.resource://${getApplication<Application>().packageName}/drawable/dessert_tiramisu"
                    ),
                    Recipe(
                        title = "Lemon Bars",
                        ingredients = "Shortbread Crust, Lemon Juice, Sugar, Eggs, Flour",
                        instructions = "Bake crust → pour lemon mix → bake again → cool & slice.",
                        category = Category.Desserts,
                        imageUri = "android.resource://${getApplication<Application>().packageName}/drawable/dessert_lemon_bars"
                    ),

                    //Other
                    Recipe(
                        title = "Homemade Granola",
                        ingredients = "Oats, Honey, Almonds, Coconut, Dried Fruit",
                        instructions = "Mix → bake 20 min → stir halfway → cool.",
                        category = Category.Other,
                        imageUri = "android.resource://${getApplication<Application>().packageName}/drawable/other_granola"
                    ),
                    Recipe(
                        title = "Guacamole",
                        ingredients = "Avocados, Lime, Onion, Tomato, Cilantro, Salt",
                        instructions = "Mash avocados → fold in chopped veg → season.",
                        category = Category.Other,
                        imageUri = "android.resource://${getApplication<Application>().packageName}/drawable/other_guacamole"
                    ),
                    Recipe(
                        title = "Fruit Smoothie",
                        ingredients = "Mixed Berries, Banana, Yogurt, Honey, Ice",
                        instructions = "Blend until smooth; serve cold.",
                        category = Category.Other,
                        imageUri = "android.resource://${getApplication<Application>().packageName}/drawable/other_smoothie"
                    ),
                    Recipe(
                        title = "Hummus",
                        ingredients = "Chickpeas, Tahini, Garlic, Lemon, Olive Oil, Cumin",
                        instructions = "Blend all ingredients until creamy.",
                        category = Category.Other,
                        imageUri = "android.resource://${getApplication<Application>().packageName}/drawable/other_hummus"
                    )
                )

                mockRecipes.forEach { dao.insert(it) }
            }
        }
    }
}
