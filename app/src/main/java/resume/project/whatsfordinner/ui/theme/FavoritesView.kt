package resume.project.whatsfordinner.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.Flow
import resume.project.whatsfordinner.data.FavoriteMeal

@Composable
fun FavoritesView(
    modifier: Modifier = Modifier,
    favoriteMeals: Flow<List<FavoriteMeal>>,
    navigateToDetail: (String) -> Unit,
    fetchMealDetails: (String) -> Unit,
    removeFromFavorites: (FavoriteMeal) -> Unit
) {
    val meals by favoriteMeals.collectAsState(emptyList())

    Box(modifier = modifier.fillMaxSize()) {
        if (meals.isEmpty()) {
            Text(
                text = "Add some favorites to get started.",
                modifier = Modifier
                    .align(Alignment.Center),
                fontFamily = Swansea,
                fontSize = 20.sp,
                color = dark_green
                )
        } else {
            FavoriteScreen(
                meals = meals.sortedBy { it.strMeal },
                navigateToDetail = navigateToDetail,
                fetchMealDetails = fetchMealDetails,
                removeFromFavorites = removeFromFavorites
            )
        }
    }
}

@Composable
fun FavoriteScreen(
    meals: List<FavoriteMeal>,
    navigateToDetail: (String) -> Unit,
    fetchMealDetails: (String) -> Unit,
    removeFromFavorites: (FavoriteMeal) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(creamy_white)
    ) {
        items(meals) { meal ->
            FavoriteMealItem(
                meal = meal,
                navigateToDetail = navigateToDetail,
                fetchMealDetails = fetchMealDetails,
                removeFromFavorites = removeFromFavorites
            )
        }
    }
}

@Composable
fun FavoriteMealItem(
    meal: FavoriteMeal,
    navigateToDetail: (String) -> Unit,
    fetchMealDetails: (String) -> Unit,
    removeFromFavorites: (FavoriteMeal) -> Unit
) {

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                navigateToDetail(meal.idMeal)
                fetchMealDetails(meal.idMeal)
            },
        backgroundColor = Color.White,
        elevation = 2.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = meal.strMeal,
                color = dark_green,
                fontFamily = Swansea,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = { removeFromFavorites(meal) },
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Remove from Favorites",
                    tint = dark_green
                )
            }
        }
    }
}