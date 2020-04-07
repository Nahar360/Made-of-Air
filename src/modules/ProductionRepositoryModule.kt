package com.madeofair.modules

import com.madeofair.repositories.MusicRepository
import com.madeofair.repositories.UsersRepository

object ProductionRepositoryModule : RepositoryModule {
    override fun users() = UsersRepository
    override fun music() = MusicRepository
}
