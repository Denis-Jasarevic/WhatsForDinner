package resume.project.whatsfordinner.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import resume.project.whatsfordinner.MainViewModel
import resume.project.whatsfordinner.MealDetails
import resume.project.whatsfordinner.data.FavoriteMeal

@Composable
fun MealDetailView(
    modifier: Modifier = Modifier,
    mealDetailState: MainViewModel.MealDetailState,
    addToFavorites: (String, String) -> Unit,
    removeFromFavorites: (FavoriteMeal) -> Unit,
    isFavorite: Boolean
) {
    Box(modifier = modifier.fillMaxSize()) {
        when {
            mealDetailState.loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            mealDetailState.error != null -> {
                Text("Error Occurred: ${mealDetailState.error}", modifier = Modifier.align(Alignment.Center))
            }
            mealDetailState.mealDetails.isNotEmpty() -> {
                val selectedMeal = mealDetailState.mealDetails.first()
                MealDetailScreen(
                    meal = selectedMeal,
                    addToFavorites = {idMeal, mealName -> addToFavorites(idMeal, mealName)},
                    removeFromFavorites = { meal ->
                        removeFromFavorites(FavoriteMeal(idMeal = meal.idMeal, strMeal = meal.strMeal))},
                    isFavorite = isFavorite
                )
            }
            else -> {
                Text("Meal not found", modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun FavoriteButton(
    text: String,
    onClick: () -> Unit,
    contentColor: Color = creamy_white,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Bold,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = contentColor,
            fontSize = fontSize,
            fontWeight = fontWeight
        )
    }
}



@Composable
fun MealDetailScreen(
    meal: MealDetails,
    addToFavorites: (String, String) -> Unit,
    removeFromFavorites: (FavoriteMeal) -> Unit,
    isFavorite: Boolean
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Card(
            modifier = Modifier
                .padding(8.dp),
            elevation = 4.dp,
            shape = RoundedCornerShape(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(creamy_white)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .border(
                            BorderStroke(2.dp, soft_pale_red),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(8.dp)
                )
                {
                    FavoriteButton(
                        text = if (isFavorite) "Remove from Favorites" else "Add to Favorites",
                        onClick = {
                            if (isFavorite) {
                                removeFromFavorites(
                                    FavoriteMeal(
                                        idMeal = meal.idMeal,
                                        strMeal = meal.strMeal
                                    )
                                )
                            } else {
                                addToFavorites(meal.idMeal, meal.strMeal)
                            }
                        },
                        contentColor = soft_pale_red,
                    )
                }
            }
        }
        Card(
            modifier = Modifier
                .padding(8.dp),
            elevation = 4.dp,
            shape = RoundedCornerShape(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(creamy_white)
            ) {
                Row(
                    modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .border(
                        BorderStroke(2.dp, soft_pale_red),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp))
                {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically),
                    ) {
                        Text(
                            text = meal.strMeal,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp),
                            color = soft_pale_red
                        )
                        Text(
                            text = "Category: ${meal.strCategory}",
                            fontSize = 18.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                    Image(
                        painter = rememberAsyncImagePainter(meal.strMealThumb),
                        contentDescription = null,
                        modifier = Modifier
                            .width(128.dp)
                            .height(128.dp)
                            .padding(start = 8.dp)
                            .align(Alignment.CenterVertically)
                    )
                }
            }
        }
        Card(
            modifier = Modifier
                .padding(8.dp),
            elevation = 4.dp,
            shape = RoundedCornerShape(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(creamy_white)
            ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .border(
                                BorderStroke(2.dp, soft_pale_red),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp),
                    ) {
                        Text(
                            text = "Instructions:",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp),
                            color = soft_pale_red
                        )
                        Text(
                            text = meal.strInstructions,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
                }
            }
        Card(
            modifier = Modifier
                .padding(8.dp),
            elevation = 4.dp,
            shape = RoundedCornerShape(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(creamy_white)
            ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .border(
                                BorderStroke(2.dp, soft_pale_red),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp),
                    ) {
                        Text(
                            text = "Ingredients:",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp),
                            color = soft_pale_red
                        )
                        for (i in 1..20) {
                            val ingredient = meal.getIngredient(i)
                            val measure = meal.getMeasure(i)
                            if (!ingredient.isNullOrBlank() && !measure.isNullOrBlank()) {
                                Text(text = "$ingredient: $measure", fontSize = 16.sp)
                            }
                        }
                    }
            }
        }
    }
}
