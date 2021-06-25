package com.samriddha.composepokedexapplication.data.remote

import com.samriddha.composepokedexapplication.data.remote.response.pokemonDetailsModels.PokemonData
import com.samriddha.composepokedexapplication.data.remote.response.pokemonListModels.PokemonList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset:Int,
        @Query("limit") limit:Int
    ): PokemonList

    @GET("pokemon/{pokemon_name}")
    suspend fun getPokemonDetails(
        @Path("pokemon_name") pokemonName:String
    ) : PokemonData
}