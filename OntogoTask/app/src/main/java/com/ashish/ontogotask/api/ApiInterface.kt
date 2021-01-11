package com.ashish.ontogotask.api

import com.ashish.ontogotask.pojo.LoginResponse
import com.ashish.ontogotask.pojo.RegistrationResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {
    @POST("Influencer_login.php")
    @FormUrlEncoded
    fun getLogin(@Field("email") email:String, @Field("password") password:String?="abc@123"):Call<LoginResponse>

    @POST("registration.php")
    @FormUrlEncoded
    fun getRegister(@Field("email") email:String, @Field("password") password:String?="abc@123",
                    @Field("name") name:String?="abc", @Field("phone") phone:String?="12345678790"):Call<RegistrationResponse>
}