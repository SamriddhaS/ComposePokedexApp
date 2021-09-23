<h1 align="center">Compose Pokedex App</h1>

<a href="https://developer.android.com/jetpack/compose?gclid=CjwKCAjwy7CKBhBMEiwA0Eb7as8azqGQ4MjZmb9kXcCinKwFv1Mppge_uux0AFA-YiyYgNHaVMhrmhoCrHsQAvD_BwE&gclsrc=aw.ds"><img alt="JetpackCompose" src="https://img.shields.io/badge/Android%20Jetpack-Compose-%23ff1a66"/></a>
<a href="https://proandroiddev.com/exploring-dagger-hilt-and-whats-main-differences-with-dagger-android-c8c54cd92f18"><img alt="Medium" src="https://skydoves.github.io/badges/Story-Medium.svg"/></a>
  <a href="https://github.com/SamriddhaS"><img alt="Profile" src="https://img.shields.io/badge/Github-Samriddha-%2300b386"/></a> 
</p>

<p align="center">  
Compose pokedex app is a small application based on modern Android application tech-stacks and MVVM architecture.<br>This project is focusing especially on the new library jetpack compose which is a new way of making design/layouts for android apps.<br>
Also fetching data from the network via repository pattern.
</p>
</br>

<p align="center">
<img src="/screenshots/screenshot_2.jpeg"/>
</p>

## Download
Go to the [Releases](https://github.com/skydoves/SamriddhaS/ComposePokedexApp/releases) to download the latest APK.


<img src="/previews/preview.gif" align="right" width="32%"/>

## Tech stack & Open-source libraries
- Minimum SDK level 21
- [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) 
- [Jetpack Compose](https://developer.android.com/jetpack/compose?gclid=CjwKCAjwy7CKBhBMEiwA0Eb7as8azqGQ4MjZmb9kXcCinKwFv1Mppge_uux0AFA-YiyYgNHaVMhrmhoCrHsQAvD_BwE&gclsrc=aw.ds) for making designs and layouts.
- [Hilt](https://dagger.dev/hilt/) for dependency injection.
- JetPack
  - Lifecycle - dispose of observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
- Architecture
  - MVVM Architecture (View - ViewModel - Model)
  - Repository pattern
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - construct the REST APIs and paging network data.
- [Timber](https://github.com/JakeWharton/timber) - logging.


## Architecture
Pokedex is based on MVVM architecture and a repository pattern.

![architecture](https://user-images.githubusercontent.com/24237865/77502018-f7d36000-6e9c-11ea-92b0-1097240c8689.png)

## Open API

<img src="https://user-images.githubusercontent.com/24237865/83422649-d1b1d980-a464-11ea-8c91-a24fdf89cd6b.png" align="right" width="21%"/>

Pokedex using the [PokeAPI](https://pokeapi.co/) for constructing RESTful API.<br>
PokeAPI provides a RESTful API interface to highly detailed objects built from thousands of lines of data related to Pokémon.

## Find this repository useful? :heart:
Support it by joining __[stargazers](https://github.com/SamriddhaS/ComposePokedexApp/stargazers)__ for this repository. :star: <br>
And __[follow](https://github.com/SamriddhaS)__ me on github! 🤩
