package com.example.submission2.main

import android.util.Log
import androidx.lifecycle.*
import com.example.submission2.API.RetrofitClient
import com.example.submission2.model.UserRespon
import com.example.submission2.model.User
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class MainViewmodel(private val pref: SettingPreferences) : ViewModel(){

    val ListUsers = MutableLiveData<ArrayList<User>>()

    fun setSearchUsers (query: String){
        RetrofitClient.apiInstance
            .getSearchUser(query)
            .enqueue(object : Callback<UserRespon> {
                override fun onResponse(
                    call: Call<UserRespon>,
                    response: Response<UserRespon>)
                {
                    if(response.isSuccessful)
                        ListUsers.postValue(response.body()?.items)
                }

                override fun onFailure(call: Call<UserRespon>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }

            })
    }

    fun getSearchUsers(): LiveData<ArrayList<User>>{

        return ListUsers
    }

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }

}
