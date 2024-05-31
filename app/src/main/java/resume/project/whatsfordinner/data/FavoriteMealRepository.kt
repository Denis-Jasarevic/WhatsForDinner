package resume.project.whatsfordinner.data

import kotlinx.coroutines.flow.Flow

class FavoriteMealRepository(private val favoriteMealDao: FavoriteMealDao) {

    val favoriteMeals: Flow<List<FavoriteMeal>> = favoriteMealDao.getAllFavoriteMeals()

    fun getAllFavoriteMeals(): Flow<List<FavoriteMeal>> {
        return favoriteMealDao.getAllFavoriteMeals()
    }

    suspend fun addFavorite(favoriteMeal: FavoriteMeal) {
        favoriteMealDao.insertFavoriteMeal(favoriteMeal)
    }

    suspend fun removeFavorite(favoriteMeal: FavoriteMeal) {
        favoriteMealDao.deleteFavoriteMeal(favoriteMeal)
    }

    fun isFavorite(idMeal: String): Flow<Boolean> {
        return favoriteMealDao.isFavorite(idMeal)
    }
}