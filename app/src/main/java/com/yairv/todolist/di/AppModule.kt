package com.yairv.todolist.di

import android.app.Application
import androidx.room.Room
import com.yairv.todolist.data.ToDoReositoryImpl
import com.yairv.todolist.data.ToDoRepository
import com.yairv.todolist.data.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideToDoDatabase(app: Application): TodoDatabase {
        return Room.databaseBuilder(
            app,
            TodoDatabase::class.java,
            "todo_db",
        ).build()
    }

    @Provides
    @Singleton
    fun provideToDoRepository(db: TodoDatabase): ToDoRepository {
        return ToDoReositoryImpl(db.dao)
    }
}
