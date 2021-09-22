package com.samriddha.composepokedexapplication.ui.pokemon_details

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.bitmap.BitmapPool
import coil.request.ImageRequest
import coil.size.Size
import coil.transform.Transformation
import com.google.accompanist.coil.rememberCoilPainter
import com.samriddha.composepokedexapplication.R
import com.samriddha.composepokedexapplication.data.remote.response.pokemonDetailsModels.PokemonData
import com.samriddha.composepokedexapplication.data.remote.response.pokemonDetailsModels.Type
import com.samriddha.composepokedexapplication.utils.Resource
import com.samriddha.composepokedexapplication.utils.parseTypeToColor
import kotlin.math.round

@Composable
fun PokemonDetailsScreen(
    dominantColor: Color,
    pokemonName:String,
    navController: NavController,
    topPadding:Dp=20.dp,
    pokemonImageSize:Dp=200.dp,
    viewModel: PokemonDetailViewModel= hiltViewModel()
) {

    val pokemonInfo= produceState<Resource<PokemonData>>(initialValue = Resource.Loading() ){
        value = viewModel.getPokemonInfo(pokemonName)
    }.value

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = dominantColor)
        .padding(bottom = 18.dp)
    ) {


        PokemonDetailsTopSection(
            navController = navController,
            modifier = Modifier
                .fillMaxHeight(0.2f)
                .fillMaxWidth()
                .align(Alignment.TopCenter)
            )

        /*
        * White box section
        * */
        PokemonDetailsStateWrapper(
            pokemonInfo = pokemonInfo,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = topPadding + pokemonImageSize / 2f,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 18.dp
                )
                .shadow(10.dp, RoundedCornerShape(18.dp))
                .clip(RoundedCornerShape(14.dp))
                .background(MaterialTheme.colors.surface)
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            loadingModifier = Modifier
                .size(100.dp)
                .align(Alignment.Center)
                .padding(
                    top = topPadding + pokemonImageSize / 2f,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 18.dp
                )
            )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
            ) {
            if (pokemonInfo is Resource.Success){
                pokemonInfo.data?.sprites?.let {

                    val painter = rememberCoilPainter(request = ImageRequest.Builder(context = LocalContext.current)
                        .data(it.front_default)
                        .placeholder(R.drawable.ic_international_pok_mon_logo)
                        .build()
                    )

                    Image(painter = painter,
                        contentDescription = pokemonInfo.data.name,
                        modifier = Modifier
                            .size(pokemonImageSize)
                            .offset(y = topPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun PokemonDetailsTopSection(
    navController: NavController,
    modifier: Modifier = Modifier

) {
    Box(
        contentAlignment = Alignment.TopStart,
        modifier = modifier
            .background(
                Brush.Companion.verticalGradient(
                colors = listOf(
                    Color.Black,
                    Color.Transparent
                ),
                    startY = 0f,
                    endY = 500f,
                    tileMode = TileMode.Repeated
            ))
    ) {

        Icon(imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(36.dp)
                .offset(18.dp, 18.dp)
                .clickable {
                    navController.popBackStack()
                }
            )
    }
}

@Composable
fun PokemonDetailsStateWrapper(
    pokemonInfo:Resource<PokemonData>,
    modifier:Modifier=Modifier,
    loadingModifier:Modifier=Modifier
) {
    when(pokemonInfo){
        is Resource.Success -> {
            PokemonDetailsSection(
                pokemonInfo = pokemonInfo.data!!,
                modifier = modifier.offset(y = (-20).dp)
                )
        }
        is Resource.Error -> {
            Text(text = pokemonInfo.message!!,
                color = Color.Red,
                modifier = modifier
                )
        }
        is Resource.Loading -> {
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary,
                modifier = loadingModifier
            )
        }
    }
}

@Composable
fun PokemonDetailsSection(
    pokemonInfo:PokemonData,
    modifier: Modifier=Modifier
) {

    val scrollState = rememberScrollState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .offset(y = 100.dp)
            .verticalScroll(scrollState)
    ) {

        Text(
            text = "#${pokemonInfo.id} ${pokemonInfo.name.capitalize(Locale.current)}",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onSurface
        )

        PokemonTypeSection(types = pokemonInfo.types)

        PokemonDetailDataSection(
            pokemonWeight = pokemonInfo.weight,
            pokemonHeight = pokemonInfo.height
        )



    }

}

@Composable
fun PokemonTypeSection(types:List<Type>) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
    ) {

        for (type in types){
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .clip(CircleShape)
                    .background(parseTypeToColor(type))
                    .height(36.dp)
            ) {

                Text(
                    text = type.type.name.capitalize(Locale.current),
                    color = Color.White,
                    fontSize = 18.sp
                    )
            }
        }
    }
}

@Composable
fun PokemonDetailDataSection(
    pokemonWeight:Int,
    pokemonHeight:Int,
    sectionHeight:Dp = 80.dp
) {
    val pokemonWeightInKg = remember {
        round(pokemonWeight*100f) / 1000f
    }

    val pokemonHeightInMeters = remember {
        round(pokemonHeight*100f) / 1000f
    }

    Row(
        modifier =  Modifier
            .fillMaxWidth()
    )
    {

        PokemonDetailsDataItem(
            dataValue = pokemonWeightInKg,
            dataUnit = "kg",
            dataIcon = painterResource(id = R.drawable.ic_weight),
            modifier = Modifier.weight(1f)
            )

        Spacer(modifier = Modifier
            .size(1.dp, sectionHeight)
            .background(color = Color.LightGray)
        )

        PokemonDetailsDataItem(
            dataValue = pokemonHeightInMeters,
            dataUnit = "m",
            dataIcon = painterResource(id = R.drawable.ic_height),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun PokemonDetailsDataItem(
    dataValue:Float,
    dataUnit:String,
    dataIcon:Painter,
    modifier: Modifier=Modifier
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Icon(
            painter = dataIcon,
            contentDescription =null,
            tint = MaterialTheme.colors.onSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "$dataValue $dataUnit",
            color = MaterialTheme.colors.onSurface
            )
    }
}