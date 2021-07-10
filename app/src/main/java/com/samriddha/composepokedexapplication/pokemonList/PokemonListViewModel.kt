package com.samriddha.composepokedexapplication.pokemonList

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.samriddha.composepokedexapplication.data.models.PokemonListEntry
import com.samriddha.composepokedexapplication.data.repository.PokemonRepository
import com.samriddha.composepokedexapplication.utils.Constants.PAGE_SIZE
import com.samriddha.composepokedexapplication.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel
@Inject
constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private var currentPage=0

    var pokemonList = mutableStateOf<List<PokemonListEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    fun loadPokemonPaginated(){
        viewModelScope.launch {
            isLoading.value=true
            val result = repository.getPokemonList(PAGE_SIZE,currentPage*PAGE_SIZE)
            when(result){
                is Resource.Success -> {
                    endReached.value = currentPage* PAGE_SIZE>=result.data!!.count
                    val pokemonEntries = result.data.results.mapIndexed { _ , entries ->
                        val number = if (entries.url.endsWith("/")){
                            entries.url.dropLast(1).takeLastWhile { it.isDigit() }
                        }else {
                            entries.url.takeLastWhile { it.isDigit() }
                        }
                        val url = "https://raw.githubusercontent.com/PokeApi/sprites/master/sprites/pokemon/${number}.png"
                        PokemonListEntry(entries.name.capitalize(),url,number.toInt())
                    }
                    currentPage++
                    loadError.value=""
                    isLoading.value=false
                    pokemonList.value = pokemonEntries
                }
                is Resource.Error -> {
                    loadError.value=result.message!!
                    isLoading.value=false
                }
            }

        }
    }

    fun getDominantColorFromDrawable(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888,true)

        /*Getting the dominant color from image
        * using the Pallet library.
        * */
        Palette.from(bmp).generate {
            it?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }
}