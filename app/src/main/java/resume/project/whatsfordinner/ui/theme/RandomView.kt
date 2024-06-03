package resume.project.whatsfordinner.ui.theme

import android.content.Intent
import android.net.Uri
import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import resume.project.whatsfordinner.MainViewModel
import resume.project.whatsfordinner.R
import resume.project.whatsfordinner.RandomDetails
import resume.project.whatsfordinner.data.FavoriteMeal


@Composable
fun RandomView(
    modifier: Modifier = Modifier,
    randomMealState: MainViewModel.RandomMealState,
    fetchRandomMeal: () -> Unit,
    addToFavorites: (String, String) -> Unit,
    removeFromFavorites: (FavoriteMeal) -> Unit,
    isFavorite: Boolean
){
    Box(modifier = modifier.fillMaxSize()) {
        when {
            randomMealState.loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            randomMealState.error != null -> {
                Text("Error Occurred: ${randomMealState.error}", modifier = Modifier.align(Alignment.Center))
            }
            randomMealState.randomMeal.isNotEmpty() -> {
                RandomScreen(
                    randomMealState.randomMeal[0],
                    fetchRandomMeal,
                    addToFavorites = {idMeal, mealName -> addToFavorites(idMeal, mealName)},
                    removeFromFavorites = { randomMeal ->
                        removeFromFavorites(FavoriteMeal(idMeal = randomMeal.idMeal, strMeal = randomMeal.strMeal))},
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
fun RandomButton(
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
fun RandomScreen(
    randomMeal: RandomDetails,
    fetchRandomMeal: () -> Unit,
    addToFavorites: (String, String) -> Unit,
    removeFromFavorites: (FavoriteMeal) -> Unit,
    isFavorite: Boolean
){
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(vanilla)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
            ) {
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 60.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .width(384.dp)
                            .height(384.dp)
                            .padding(8.dp),
                        elevation = 20.dp,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(randomMeal.strMealThumb),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(top = 8.dp, start = 20.dp, end = 20.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .wrapContentWidth()
                            .background(
                                color = vanilla_contrast,
                                shape = RoundedCornerShape(8.dp)
                            ),
                    ) {
                        Text(
                            text = randomMeal.strCategory.uppercase(),
                            fontFamily = Swansea,
                            fontSize = 16.sp,
                            color = dark_green,
                            modifier = Modifier
                                .padding(8.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(top = 25.dp, start = 20.dp, end = 20.dp, bottom = 25.dp)
                ){
                    Text(
                        text = randomMeal.strMeal,
                        fontFamily = Swansea,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = dark_green,
                        style = TextStyle(
                            lineHeight = 40.sp
                        ),
                        modifier = Modifier
                            .wrapContentWidth()


                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom
                ) {
                    IconButton(
                        onClick = {
                            if (isFavorite) {
                                removeFromFavorites(
                                    FavoriteMeal(
                                        idMeal = randomMeal.idMeal,
                                        strMeal = randomMeal.strMeal
                                    )
                                )
                            } else {
                                addToFavorites(randomMeal.idMeal, randomMeal.strMeal)
                            }
                        },
                    ) {
                        Icon(
                            imageVector = if (isFavorite) {
                                ImageVector.vectorResource(id = R.drawable.baseline_favorite_24)
                            } else {
                                ImageVector.vectorResource(id = R.drawable.baseline_favorite_border_24)
                            },
                            contentDescription = if (isFavorite) "Remove from Favorites" else "Add to Favorites",
                            tint = dark_green,
                            modifier = Modifier
                                .height(24.dp)
                                .width(24.dp)
                        )
                    }
                    IconButton(
                        onClick = {
                            fetchRandomMeal()
                        }
                    ) {
                       Icon(
                           imageVector = ImageVector.vectorResource(
                               id = R.drawable.baseline_sync_24
                           ),
                           contentDescription = "Fetch New Random Meal",
                           tint = dark_green,
                           modifier = Modifier
                               .height(24.dp)
                               .width(24.dp)
                       )
                    }
                    if (!randomMeal.strYoutube.isNullOrEmpty()) {
                        IconButton(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(randomMeal.strYoutube))
                                    .apply {
                                        putExtra("force_fullscreen", true)
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    }
                                context.startActivity(intent)
                            }
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(
                                    id = R.drawable.baseline_smart_display_24
                                ),
                                contentDescription = "Watch on YouTube",
                                tint = dark_green,
                                modifier = Modifier
                                    .height(24.dp)
                                    .width(24.dp)
                            )
                        }
                    } else {
                        IconButton(
                            onClick = {
                                Toast.makeText(
                                    context,
                                    "No YouTube link available",
                                    Toast.LENGTH_SHORT
                                ).apply {
                                    setGravity(Gravity.CENTER, 0, 0)
                                    show()
                                }
                                      },
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(
                                    id = R.drawable.baseline_smart_display_24
                                ),
                                contentDescription = "No YouTube link available",
                                tint = Color.Gray,
                                modifier = Modifier
                                    .height(24.dp)
                                    .width(24.dp)
                            )
                        }
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(creamy_white)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = "Instructions:",
                    color = dark_green,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                )
                Text(
                    text = randomMeal.strInstructions,
                    color = dark_green,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Card(
                    modifier = Modifier,
                    elevation = 2.dp,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(vanilla)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)

                    ) {
                        Text(
                            text = "Ingredients:",
                            color = dark_green,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        for (i in 1..20) {
                            val ingredient = randomMeal.getIngredient(i)
                            val measure = randomMeal.getMeasure(i)
                            if (!ingredient.isNullOrBlank() && !measure.isNullOrBlank()) {
                                Text(
                                    text = "$ingredient: $measure",
                                    color = dark_green,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .padding(start = 8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}