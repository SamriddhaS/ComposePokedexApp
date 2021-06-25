package com.samriddha.composepokedexapplication.data.remote.response.pokemonDetails

import com.google.gson.annotations.SerializedName

data class GenerationI(
    @SerializedName("red-blue")
    val redBlue: RedBlue,
    val yellow: Yellow
)