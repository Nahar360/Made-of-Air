package com.madeofair.models.domain

enum class Genres {
    AMBIENT,
    ART_POP,
    ART_ROCK,
    BAROQUE_POP,
    CHAMBER_POP,
    CLASSICAL,
    COUNTRY,
    DREAM_POP,
    DUBSTEP,
    ELECTRO_POP,
    ELECTRONIC,
    EXPERIMENTAL,
    FLAMENCO,
    FOLK,
    FOLK_ROCK,
    FUNK,
    GARAGE_ROCK,
    HOUSE,
    INDIE,
    INDIE_FOLK,
    INDIE_POP,
    INDIE_ROCK,
    INDUSTRIAL,
    INSTRUMENTAL,
    JAZZ,
    LO_FI,
    METAL,
    NEOFOLK,
    NOISE,
    NOISE_POP,
    NOISE_ROCK,
    OST,
    POP,
    POP_ROCK,
    POST_DUBSTEP,
    POST_METAL,
    POST_ROCK,
    PSYCHEDELIC_ROCK,
    PSYCHEDELIC_POP,
    PUNK,
    PUNK_ROCK,
    R_AND_B,
    RAP,
    ROCK,
    SHOEGAZE,
    SOUL,
    SPANISH_INDIE_POP,
    SPANISH_INDIE_ROCK,
    SYMPHONIC_METAL,
    SYNTHPOP,
    WORLD_MUSIC,
    NONE
}

fun genreStringToGenreEnum(genre: String): Genres {
    return when (genre) {
        "Ambient" -> Genres.AMBIENT
        "Art Pop" -> Genres.ART_POP
        "Art Rock" -> Genres.ART_ROCK
        "Baroque Pop" -> Genres.BAROQUE_POP
        "Chamber Pop" -> Genres.CHAMBER_POP
        "Classical" -> Genres.CLASSICAL
        "Country" -> Genres.COUNTRY
        "Dream Pop" -> Genres.DREAM_POP
        "Dubstep" -> Genres.DUBSTEP
        "Electro Pop" -> Genres.ELECTRO_POP
        "Electronic" -> Genres.ELECTRONIC
        "Experimental" -> Genres.EXPERIMENTAL
        "Flamenco" -> Genres.FLAMENCO
        "Folk" -> Genres.FOLK
        "Folk Rock" -> Genres.FOLK_ROCK
        "Funk" -> Genres.FUNK
        "Garage Rock" -> Genres.GARAGE_ROCK
        "House" -> Genres.HOUSE
        "Indie" -> Genres.INDIE
        "Indie Folk" -> Genres.INDIE_FOLK
        "Indie Pop" -> Genres.INDIE_POP
        "Indie Rock" -> Genres.INDIE_ROCK
        "Industrial" -> Genres.INDUSTRIAL
        "Instrumental" -> Genres.INSTRUMENTAL
        "Jazz" -> Genres.JAZZ
        "Lo-Fi" -> Genres.LO_FI
        "Metal" -> Genres.METAL
        "Neofolk" -> Genres.NEOFOLK
        "Noise" -> Genres.NOISE
        "Noise Pop" -> Genres.NOISE_POP
        "Noise Rock" -> Genres.NOISE_ROCK
        "OST" -> Genres.OST
        "Pop" -> Genres.POP
        "Pop Rock" -> Genres.POP_ROCK
        "Post Dubstep" -> Genres.POST_DUBSTEP
        "Post Metal" -> Genres.POST_METAL
        "Post Rock" -> Genres.POST_ROCK
        "Psychedelic Rock" -> Genres.PSYCHEDELIC_ROCK
        "Psychedelic Pop" -> Genres.PSYCHEDELIC_POP
        "Punk" -> Genres.PUNK
        "Punk Rock" -> Genres.PUNK_ROCK
        "R&B" -> Genres.R_AND_B
        "Rap" -> Genres.RAP
        "Rock" -> Genres.ROCK
        "Shoegaze" -> Genres.SHOEGAZE
        "Soul" -> Genres.SOUL
        "Spanish Indie Pop" -> Genres.SPANISH_INDIE_POP
        "Spanish Indie Rock" -> Genres.SPANISH_INDIE_ROCK
        "Symphonic Metal" -> Genres.SYMPHONIC_METAL
        "Synthpop" -> Genres.SYNTHPOP
        "World Music" -> Genres.WORLD_MUSIC
        else -> Genres.NONE
    }
}

fun genreStringToGenreEnumFromDropDown(genre: String): Genres {
    return genreStringToGenreEnum(
        genre.toLowerCase().replace("_", " ").split(" ").joinToString(" ") { it.capitalize() })
}

fun getAllGenresString(): ArrayList<String> {
    val genresString = ArrayList<String>()

    for (genre in Genres.values()) {
        if (genre != Genres.NONE) {
            genresString.add(genre.name.replace("_", " ").toLowerCase().split(" ").map { it.toLowerCase().capitalize() }
                .joinToString(" "))
        }
    }

    return genresString
}
