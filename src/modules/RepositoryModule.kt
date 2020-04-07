package com.madeofair.modules

import com.madeofair.repositories.MusicRepository
import com.madeofair.repositories.UsersRepository

interface RepositoryModule {
    fun users(): UsersRepository
    fun music(): MusicRepository
}
