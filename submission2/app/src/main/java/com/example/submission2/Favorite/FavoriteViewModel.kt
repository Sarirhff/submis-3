package com.example.submission2.Favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.submission2.database.DatabaseUser
import com.example.submission2.database.UserFavorite
import com.example.submission2.database.UserFavoriteDao

class FavoriteViewModel (application: Application): AndroidViewModel(application) {

    private var userDao: UserFavoriteDao?
    private var userDb : DatabaseUser?

    init {
        userDb = DatabaseUser.getDatabase(application)
        userDao = userDb?.userFavoriteDao()
    }

    fun getUserFavorite(): LiveData<List<UserFavorite>>?{
        return userDao?.getUserFavorite()
    }
}