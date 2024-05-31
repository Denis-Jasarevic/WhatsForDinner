package resume.project.whatsfordinner.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import resume.project.whatsfordinner.MainViewModel
import resume.project.whatsfordinner.MealDetails


@Composable
fun MealView(
    modifier: Modifier = Modifier,
    mealsState: MainViewModel.MealState,
    meals: List<MealDetails>,
    navigateToDetail: (String) -> Unit,
    fetchMealDetails: (String) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            mealsState.loading -> {
                CircularProgressIndicator(modifier.align(Alignment.Center))
            }
            mealsState.error != null -> {
                Text("Error Occurred", modifier = Modifier.align(Alignment.Center))
            }
            else -> {
                MealScreen(
                    meals = meals,
                    navigateToDetail = navigateToDetail,
                    fetchMealDetails = fetchMealDetails
                )
            }
        }
    }
}

@Composable
fun MealScreen(
    meals: List<MealDetails>,
    navigateToDetail: (String) -> Unit,
    fetchMealDetails: (String) -> Unit
) {
    LazyVerticalGrid(
        GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ){
        items(meals) { meal ->
            MealItem(
                meal = meal,
                navigateToDetail = navigateToDetail,
                fetchMealDetails = fetchMealDetails
            )
        }
    }
}

@Composable
fun MealItem(
    meal: MealDetails,
    navigateToDetail: (String) -> Unit,
    fetchMealDetails: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                navigateToDetail(meal.idMeal)
                fetchMealDetails(meal.idMeal)
            },
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
                    .padding(8.dp)
                    .border(BorderStroke(2.dp, soft_pale_red), shape = RoundedCornerShape(8.dp))
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = rememberAsyncImagePainter(meal.strMealThumb),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .aspectRatio(1f)
                )
                Text(
                    text = meal.strMeal,
                    color = soft_pale_red,
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}


