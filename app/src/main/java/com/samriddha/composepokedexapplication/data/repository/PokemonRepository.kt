package com.samriddha.composepokedexapplication.data.repository

import com.samriddha.composepokedexapplication.data.remote.PokeApi
import com.samriddha.composepokedexapplication.data.remote.response.pokemonDetails.PokemonData
import com.samriddha.composepokedexapplication.data.remote.response.pokemonList.PokemonList
import com.samriddha.composepokedexapplication.utils.Resource
import dagger.hilt.android.scopes.ActivityScoped
import java.lang.Exception
import javax.inject.Inject

@ActivityScoped
class PokemonRepository
@Inject
constructor(
    private val api:PokeApi
)
{
    suspend fun getPokemonList(limit:Int,offset:Int):Resource<PokemonList>{
        val response = try{
            api.getPokemonList(offset,limit)
        }catch (e:Exception){
            return Resource.Error(message = "Opps! an error occured.")
        }
        return Resource.Success(response)
    }

    suspend fun getPokemonByName(pokemonName: String):Resource<PokemonData>{
        val response = try{
            api.getPokemonDetails(pokemonName)
        }catch (e:Exception){
            return Resource.Error(message = "Opps! an error occured.")
        }
        return Resource.Success(response)
    }

}