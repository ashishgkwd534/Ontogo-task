package com.ashish.ontogotask.api

import com.ashish.ontogotask.utils.Utils.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Api {
    companion object{
        fun getRetrofit(): ApiInterface =
            Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiInterface::class.java)
    }
}