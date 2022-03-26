package com.example.submission2.Detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission2.API.RetrofitClient
import com.example.submission2.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VMFollowers: ViewModel() {
    val listfollow = MutableLiveData<ArrayList<User>>()

    fun setlistfollow(usernm : String){
        RetrofitClient.apiInstance
            .getUserFollowers(usernm)
            .enqueue(object : Callback<ArrayList<User>>{
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if(response.isSuccessful){
                        listfollow.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }
            })
    }
    fun getlistfollowers(): LiveData<ArrayList<User>>{
        return listfollow
    }

}