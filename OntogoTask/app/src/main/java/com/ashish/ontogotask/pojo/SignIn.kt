package com.ashish.ontogotask.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class LoginResponse {
    @SerializedName("userid")
    @Expose
    var userid: String? = null

    @SerializedName("success")
    @Expose
    var success: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("displayname")
    @Expose
    var displayname: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("img_url")
    @Expose
    var imgUrl: String? = null

    @SerializedName("message")
    @Expose
    var message: String? = null
}

class RegistrationResponse {
    @SerializedName("userid")
    @Expose
    var userid: Int? = null

    @SerializedName("success")
    @Expose
    var success: Int? = null

    @SerializedName("message")
    @Expose
    var message: String? = null
}