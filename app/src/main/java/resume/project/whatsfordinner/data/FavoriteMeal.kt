package resume.project.whatsfordinner.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_meals")
data class FavoriteMeal(
    @PrimaryKey
    val idMeal:String,
    val strMeal:String
)