package resume.project.whatsfordinner

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Meal(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String):Parcelable

data class MealsResponse(val meals: List<Meal>)