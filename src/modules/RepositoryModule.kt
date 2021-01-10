package com.madeofair.modules

import com.madeofair.repositories.*

interface RepositoryModule {
    fun users(): UsersRepository
    fun music(): MusicRepository
    fun pitchfork(): PitchforkRepository
    fun postsMusic(): PostsMusicRepository
    fun postsPitchfork(): PostsPitchforkRepository
    fun articles(): ArticlesRepository
}
