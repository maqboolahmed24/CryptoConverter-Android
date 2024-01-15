package com.project.cryptoconverter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRepository {

    var apiService: ApiInterface = ApiClient.getApiClient().create(ApiInterface::class.java)

    fun fetchAllCrypto(): LiveData<List<Data>> {
        val data = MutableLiveData<List<Data>>()

        apiService.fetchAllCrypto().enqueue(object : Callback<List<Data>> {
            override fun onFailure(call: Call<List<Data>>, t: Throwable) {
              data.value = null
            }

            override fun onResponse(
                call: Call<List<Data>>,
                response: Response<List<Data>>
            ) {

                val res = response.body()
                if (response.code() == 200 &&  res!=null){
                    data.value = res
                }else{
                    data.value = null
                }
            }
        })
        return data
    }

}