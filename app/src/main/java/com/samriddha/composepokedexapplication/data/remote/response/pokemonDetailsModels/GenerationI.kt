package com.samriddha.composepokedexapplication.data.remote.response.pokemonDetailsModels

import com.google.gson.annotations.SerializedName

data class GenerationI(
    @SerializedName("red-blue")
    val redBlue: RedBlue,
    val yellow: Yellow
)