package com.samriddha.composepokedexapplication.data.remote.response.pokemonDetailsModels

import com.google.gson.annotations.SerializedName

data class GenerationV(
    @SerializedName("black-white")
    val blackWhite: BlackWhite
)