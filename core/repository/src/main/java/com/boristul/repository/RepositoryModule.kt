package com.boristul.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindNotesRepository(
        impl: NotesRepositoryImpl
    ): NotesRepository

    @Binds
    fun bindTagsRepository(
        impl: TagsRepositoryImpl
    ): TagsRepository

    @Binds
    fun bindTaskPointsRepository(
        impl: TaskPointsRepositoryImpl
    ): TaskPointsRepository
}
