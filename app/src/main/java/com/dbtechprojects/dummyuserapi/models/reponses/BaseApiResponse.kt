package com.dbtechprojects.dummyuserapi.models.reponses

import com.dbtechprojects.dummyuserapi.models.User
import com.google.gson.annotations.SerializedName

data class BaseApiResponse (

    @SerializedName("data") val data : List<User>,
    @SerializedName("total") val total : Int,
    @SerializedName("page") val page : Int,
    @SerializedName("limit") val limit : Int
)