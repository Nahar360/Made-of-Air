package com.madeofair.modules

import com.madeofair.repositories.*

object ProductionRepositoryModule : RepositoryModule {
    override fun users() = UsersRepository
    override fun music() = MusicRepository
    override fun pitchfork() = PitchforkRepository
    override fun postsMusic() = PostsMusicRepository
    override fun postsPitchfork() = PostsPitchforkRepository

}
