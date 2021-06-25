package com.samriddha.composepokedexapplication.data.remote.response.pokemonDetails

import com.google.gson.annotations.SerializedName

data class GenerationIv(
    @SerializedName("diamond-pearl")
    val diamondPearl: DiamondPearl,
    @SerializedName("heartgold-soulsilver")
    val heartGoldSoulSilver: HeartgoldSoulsilver,
    val platinum: Platinum
)