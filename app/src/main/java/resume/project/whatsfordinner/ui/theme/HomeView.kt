package resume.project.whatsfordinner.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import resume.project.whatsfordinner.Category
import resume.project.whatsfordinner.MainViewModel

@Composable
fun Home(
    modifier: Modifier = Modifier,
    viewState: MainViewModel.RecipeState,
    navigateToMeal: (Category) -> Unit,
    fetchMealsForCategory: (Category) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            viewState.loading -> {
                CircularProgressIndicator(modifier.align(Alignment.Center))
            }
            viewState.error != null -> {
                Text("Error Occurred")
            }
            else -> {
                HomeScreen(
                    categoriesWithMeals = viewState.categoriesWithMeals,
                    navigateToMeal = navigateToMeal,
                    fetchMealsForCategory = fetchMealsForCategory
                )
            }
        }
    }
}

@Composable
fun HomeScreen(
    categoriesWithMeals: List<MainViewModel.CategoryWithMeals>,
    navigateToMeal: (Category) -> Unit,
    fetchMealsForCategory: (Category) -> Unit
) {
    LazyVerticalGrid(
        GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .background(creamy_white)
    ) {
        items(categoriesWithMeals) { categoryWithMeals ->
            CategoryItem(
                category = categoryWithMeals.category,
                navigateToMeal = navigateToMeal,
                fetchMealsForCategory = fetchMealsForCategory
            )
        }
    }
}

@Composable
fun CategoryItem(
    category: Category,
    navigateToMeal: (Category) -> Unit,
    fetchMealsForCategory: (Category) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .clickable {
                navigateToMeal(category)
                fetchMealsForCategory(category)
            },
        elevation = 2.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
            ) {
                Image(
                    painter = rememberAsyncImagePainter(category.strCategoryThumb),
                    contentDescription = category.strCategory,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .aspectRatio(1f)
                )
                Text(
                    text = category.strCategory,
                    color = dark_green,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Swansea,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}