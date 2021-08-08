package com.samriddha.composepokedexapplication.ui.pokemon_list

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
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
import kotlinx.coroutines.Dispatchers
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

    private var cachedPokemonList = listOf<PokemonListEntry>()
    private var isSearchStarting = true
    var isSearching = mutableStateOf(false)

    init {
        loadPokemonPaginated()
    }

    fun searchPokemonList(query:String){
        val listToSearch= if(isSearchStarting) pokemonList.value else cachedPokemonList

        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()){
                pokemonList.value = cachedPokemonList
                isSearching.value = false
                isSearchStarting = true
                return@launch
            }

            val results = listToSearch.filter {

                /*
                * Filter pokemon list using name and number
                * */
                it.pokemonName.contains(query.trim(),ignoreCase = true)
                        || it.number.toString() == query.trim()
            }

            if (isSearchStarting){
                cachedPokemonList = pokemonList.value
                isSearchStarting = false
            }

            pokemonList.value = results
            isSearching.value = true
        }
    }

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
                    pokemonList.value += pokemonEntries
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