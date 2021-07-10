package com.samriddha.composepokedexapplication.pokemonList

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import coil.request.ImageRequest
import com.google.accompanist.coil.CoilImage
import com.samriddha.composepokedexapplication.R
import com.samriddha.composepokedexapplication.data.models.PokemonListEntry
import com.samriddha.composepokedexapplication.ui.theme.RobotoCondensed
import timber.log.Timber

@Composable
fun PokemonListScreen(navController: NavController){

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            //Initial space above pokemon logo.
            Spacer(modifier = Modifier.height(20.dp))

            //Pokemon logo image
            Image(painter = painterResource(id = R.drawable.ic_international_pok_mon_logo),
                contentDescription = "Pokemon logo",
            modifier = Modifier
                .fillMaxWidth()
                .align(CenterHorizontally))

            //Search bar
            SearchBar(
                hint = "Search Pokemon...",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(28.dp)
            ){

            }

            Spacer(modifier = Modifier.height(16.dp))

            PokemonList(navController = navController)


        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint:String="",
    onSearch: (String)->Unit = {}
){
    var text by remember {
        mutableStateOf("")
    }
    var isHintDisplayed by remember{
        mutableStateOf(hint!="")
    }

    Box(modifier = modifier) {

        /*This field will take the user input: Edit text*/
        BasicTextField(value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(6.dp, CircleShape)
                .background(Color.White, CircleShape)
                .padding(horizontal = 18.dp, vertical = 12.dp)
                .onFocusChanged {
                    isHintDisplayed = it != FocusState.Active
                }
            )

        /*For showing the hint*/
        if (isHintDisplayed) {
            Text(text = hint,
                color = Color.LightGray,
                modifier = Modifier.padding(horizontal = 18.dp, vertical = 12.dp)
                )
        }
    }
}

@Composable
fun PokemonListRow(
    rowIndex:Int,
    entries:List<PokemonListEntry>,
    navController: NavController
) {
     Column {
         Row {

             /* 1st pokemon of the row*/
             Timber.d(rowIndex.toString())
             PokemonEntry(entry = entries[rowIndex * 2],
                 navController = navController,
                 modifier = Modifier.weight(1f)
                 )

             /*For space between two pokemon inside the same row.*/
             Spacer(modifier = Modifier.width(18.dp))

             /*2nd pokemon of the row*/
             if (entries.size >= rowIndex*2+2){
                 PokemonEntry(entry = entries[rowIndex * 2 + 1],
                     navController = navController,
                     modifier = Modifier.weight(1f)
                 )
             }else {
                 Spacer(modifier = Modifier.weight(1f))
             }

         }
     }

    /*For spacing between 2 columns*/
    Spacer(modifier = Modifier.height(18.dp))
}

@Composable
fun PokemonList(
    navController: NavController,
    viewModel: PokemonListViewModel= hiltNavGraphViewModel()
){
    val pokemonList by remember{ viewModel.pokemonList }
    val endReached by remember{ viewModel.endReached }
    val isLoading by remember{ viewModel.isLoading }
    val loadError by remember{ viewModel.loadError }

    LazyColumn(contentPadding = PaddingValues(16.dp)) {

        /* This variable says how many items will be their in the list.
        *  Because we have 2 items per row so we device the total item by 2.
        * */
        val itemCount = if (pokemonList.size%2==0){
            pokemonList.size/2
        }else{
            pokemonList.size/2 + 1
        }

        items(itemCount) {

            /* check if user has scrolled to the bottom of the list so we
            * can load more items from view models.
            * */
            if (it>=itemCount-1 && !endReached ) {
                viewModel.loadPokemonPaginated()
            }

            /* Display the pokemon rows */
            PokemonListRow(rowIndex = it, entries = pokemonList, navController = navController)
        }
    }

    Box(
        contentAlignment = Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if(isLoading){
            CircularProgressIndicator(color = MaterialTheme.colors.primary)
        }
        if (loadError.isNotEmpty()){
            RetrySection(error = loadError) {
                viewModel.loadPokemonPaginated()
            }
        }
    }
}

@Composable
fun PokemonEntry(
    entry:PokemonListEntry,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PokemonListViewModel = hiltNavGraphViewModel()
) {
    /*This color will be used as dominant color if
    * the dominant color is not processed yet.
    * */
    val defaultDominantColor  = MaterialTheme.colors.surface

    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }

    Box(contentAlignment= Center,
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(
                Brush.verticalGradient(
                    listOf(
                        dominantColor,
                        defaultDominantColor
                    )
                )
            )
            .clickable {
                navController.navigate(
                    "pokemon_details_screen/${dominantColor.toArgb()}/${entry.pokemonName}"
                )
            }
        ) {
        
        Column {

            /*Show Pokemon Image */
            CoilImage(request = ImageRequest.Builder(LocalContext.current)
                .data(entry.imageUrl)
                .target {
                    viewModel.getDominantColorFromDrawable(it){color->
                        dominantColor = color
                    }
                }
                .build(),
            contentDescription = entry.pokemonName,
            fadeIn = true,
            modifier = Modifier
                .size(120.dp)
                .align(CenterHorizontally)) {

                /*This is the coli image scope.
                * This section will be shown when the actual image is getting
                * loaded from network.*/
                CircularProgressIndicator(
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.scale(0.5f)
                )
            }

            /*Show pokemon name*/
            Text(text = entry.pokemonName,
                fontFamily = RobotoCondensed,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
                )

        }
    }
}

@Composable
fun RetrySection(
    error:String,
    onRetry: () -> Unit
){
    Column {

        Text(error,color = Color.Red ,fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { onRetry() },
            modifier = Modifier.align(CenterHorizontally)
            ) {
            Text(text = "Retry")
        }

    }
}