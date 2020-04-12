package com.madeofair.modules

import com.madeofair.repositories.MusicRepository
import com.madeofair.repositories.PitchforkRepository
import com.madeofair.repositories.UsersRepository

interface RepositoryModule {
    fun users(): UsersRepository
    fun music(): MusicRepository
    fun pitchfork(): PitchforkRepository
}
