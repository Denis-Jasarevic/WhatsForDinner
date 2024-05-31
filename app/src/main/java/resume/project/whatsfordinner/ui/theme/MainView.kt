package resume.project.whatsfordinner.ui.theme

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import resume.project.whatsfordinner.Category
import resume.project.whatsfordinner.MainViewModel
import resume.project.whatsfordinner.R
import resume.project.whatsfordinner.Screen
import resume.project.whatsfordinner.screensInBottom
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.Flow
import resume.project.whatsfordinner.data.FavoriteMeal


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainView(){

    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val viewModel: MainViewModel = viewModel()
    val controller: NavController = rememberNavController()
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val isSheetFullScreen by remember{ mutableStateOf(false)}
    val modifier = if(isSheetFullScreen) Modifier.fillMaxSize() else Modifier.fillMaxWidth()

    val currentScreen:Screen = remember {
        viewModel.currentScreen.value
    }

    val title:MutableState<String> = remember{
        mutableStateOf(currentScreen.title)
    }

    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it!= ModalBottomSheetValue.HalfExpanded}
    )

    val roundedCornerRadius = if(isSheetFullScreen) 0.dp else 12.dp

    val bottomBar: @Composable () -> Unit = {
        if (currentScreen is Screen.BottomScreen.Home) {
            BottomNavigation(
                Modifier.wrapContentSize(),
                backgroundColor = soft_pale_red) {
                screensInBottom.forEach { item ->
                    val tint = if (currentRoute == item.bRoute) creamy_white else soft_pale_red_darker
                    BottomNavigationItem(
                        selected = currentRoute == item.bRoute,
                        onClick = {
                            controller.navigate(item.bRoute)
                            title.value = item.bTitle
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = item.bTitle,
                                tint = tint
                            )
                        },
                        label = { Text(text = item.bTitle, color = tint) },
                        selectedContentColor = creamy_white,
                        unselectedContentColor = soft_pale_red_darker
                    )
                }
            }
        }
    }

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(topStart = roundedCornerRadius, topEnd = roundedCornerRadius),
        sheetContent = {
            MoreBottomSheet(modifier = modifier)
        }) {
        Scaffold(
            bottomBar = bottomBar,
            topBar = {
                TopAppBar(
                    backgroundColor = soft_pale_red,
                    title = { Text(title.value, color = creamy_white) },
                    actions = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    if (modalSheetState.isVisible) modalSheetState.hide()
                                    else modalSheetState.show()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "Settings and More"
                            )
                        }
                    },
                )
            }, scaffoldState = scaffoldState,
        ) {
            Navigation(navController = controller, viewModel = viewModel, pd = it)
        }
    }
}

@Composable
fun MoreBottomSheet(modifier: Modifier){
    Box(
        Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(color = soft_pale_red)
    ){
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween){
            Row(modifier = Modifier.padding(16.dp)) {
                Icon(modifier = Modifier.padding(end = 8.dp),
                    painter = painterResource(id = R.drawable.baseline_settings_24),
                    contentDescription = "Settings",
                    tint = soft_pale_red_darker)
                Text(text = "Settings", fontSize = 20.sp, color = creamy_white)
            }
            Row(modifier = Modifier.padding(16.dp)) {
                Icon(modifier = Modifier.padding(end = 8.dp),
                    painter = painterResource(id = R.drawable.baseline_share_24),
                    contentDescription = "Share",
                    tint = soft_pale_red_darker)
                Text(text = "Share", fontSize = 20.sp, color = creamy_white)
            }
            Row(modifier = Modifier.padding(16.dp)) {
                Icon(modifier = Modifier.padding(end = 8.dp),
                    painter = painterResource(id = R.drawable.baseline_help_24),
                    contentDescription = "Help",
                    tint = soft_pale_red_darker)
                Text(text = "Help", fontSize = 20.sp, color = creamy_white)
            }
        }
    }
}

@Composable
fun Navigation(navController: NavController, viewModel: MainViewModel, pd:PaddingValues){
    val recipeViewModel: MainViewModel = viewModel()
    val viewState by viewModel.categoriesState
    val mealsState by viewModel.mealsState
    val mealDetailState by viewModel.mealDetailState
    val randomMealState by viewModel.randomMealState

    NavHost(
        navController = navController as NavHostController,
        startDestination = Screen.BottomScreen.Home.bRoute,
        modifier = Modifier.padding(pd),
    ) {
        composable(Screen.BottomScreen.Home.bRoute) {
            Home(
                viewState = viewState,
                navigateToMeal = { category ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("cat", category)
                    navController.navigate(Screen.MealView.route)
                    Log.d("Navigation", "Navigating to MealView with category: $category")
                },
                fetchMealsForCategory = recipeViewModel::fetchMealsForCategory
            )
        }
        composable(Screen.MealView.route) {
            val category = navController.previousBackStackEntry?.savedStateHandle?.get<Category>("cat")
            category?.let { selectedCategory ->
                val meals = viewState.categoriesWithMeals.find { it.category == selectedCategory }?.meals.orEmpty()
                MealView(
                    mealsState = mealsState,
                    meals = meals,
                    navigateToDetail = { idMeal ->
                        navController.currentBackStackEntry?.savedStateHandle?.set("idMeal", idMeal)
                        navController.navigate(Screen.MealDetailView.route)
                        Log.d("Navigation1", "Navigating to MealDetailView with meal ID: $idMeal")
                    },
                    fetchMealDetails = { idMeal ->
                        recipeViewModel.fetchMealDetails(idMeal)
                        Log.d("Navigation2", "Fetching meal details for meal ID: $idMeal")
                    }
                )
            }
        }
        composable(Screen.MealDetailView.route) {
            val idMeal = navController.previousBackStackEntry?.savedStateHandle?.get<String>("idMeal")
            var isFavoriteState by remember { mutableStateOf(false) }

            LaunchedEffect(idMeal) {
                if (!idMeal.isNullOrEmpty()) {
                    viewModel.isFavorite(idMeal).collect { isFavorite ->
                        isFavoriteState = isFavorite
                    }
                    viewModel.fetchMealDetails(idMeal)
                }
            }

            MealDetailView(
                mealDetailState = mealDetailState,
                addToFavorites = viewModel::addToFavorites,
                removeFromFavorites = viewModel::removeFromFavorites,
                isFavorite = isFavoriteState
            )
        }
        composable(Screen.BottomScreen.Favorites.bRoute) {
            val favoriteMeals: Flow<List<FavoriteMeal>> = viewModel.favoriteMeals

            FavoritesView(
                favoriteMeals = favoriteMeals,
                navigateToDetail = { idMeal ->
                    navController.navigate(Screen.MealDetailView.route) {
                        navController.currentBackStackEntry?.savedStateHandle?.set("idMeal", idMeal)
                    }
                },
                fetchMealDetails = { idMeal ->
                    viewModel.fetchMealDetails(idMeal)
                },
                removeFromFavorites = { meal ->
                    viewModel.removeFromFavorites(meal)
                }
            )
        }
        composable(Screen.BottomScreen.Random.bRoute) {
            val randomMeal = viewModel.randomMealState.value.randomMeal.firstOrNull()
            var isFavoriteState by remember { mutableStateOf(false) }

            LaunchedEffect(randomMeal) {
                randomMeal?.let {
                    viewModel.isFavorite(it.idMeal).collect { isFavorite ->
                        isFavoriteState = isFavorite
                    }
                }
            }

            RandomView(
                randomMealState = randomMealState,
                fetchRandomMeal = viewModel::fetchRandomMeal,
                addToFavorites = viewModel::addToFavorites,
                removeFromFavorites = viewModel::removeFromFavorites,
                isFavorite = isFavoriteState
            )
        }
    }
}
