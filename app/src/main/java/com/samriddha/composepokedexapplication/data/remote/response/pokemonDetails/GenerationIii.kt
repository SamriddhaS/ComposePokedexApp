package com.samriddha.composepokedexapplication.data.remote.response.pokemonDetails

import com.google.gson.annotations.SerializedName

data class GenerationIii(
    val emerald: Emerald,
    @SerializedName("firered-leafgreen")
    val fireredLeafGreen: FireredLeafgreen,
    @SerializedName("ruby-sapphire")
    val rubySapphire: RubySapphire
)