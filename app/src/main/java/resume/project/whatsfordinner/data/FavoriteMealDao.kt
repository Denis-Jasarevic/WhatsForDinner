package resume.project.whatsfordinner.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMealDao {

    @Query("SELECT * FROM favorite_meals")
    fun getAllFavoriteMeals(): Flow<List<FavoriteMeal>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteMeal(favoriteMeal: FavoriteMeal)

    @Delete
    suspend fun deleteFavoriteMeal(favoriteMeal: FavoriteMeal)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_meals WHERE idMeal = :idMeal)")
    fun isFavorite(idMeal: String): Flow<Boolean>
}