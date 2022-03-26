package com.example.submission2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [UserFavorite::class],
    version = 1
)
abstract class DatabaseUser : RoomDatabase(){
    companion object{
        var INSTANCE: DatabaseUser? = null
        fun getDatabase(context: Context): DatabaseUser? {
            if (INSTANCE==null){
                synchronized(DatabaseUser::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        DatabaseUser::class.java,"user_database").build()
                }
            }
            return INSTANCE
        }
    }
    abstract fun userFavoriteDao(): UserFavoriteDao
}