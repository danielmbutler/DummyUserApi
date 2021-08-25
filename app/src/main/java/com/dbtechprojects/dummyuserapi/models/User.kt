package com.dbtechprojects.dummyuserapi.models

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id : String,
    @SerializedName("title") val title : String,
    @SerializedName("firstName") val firstName : String,
    @SerializedName("lastName") val lastName : String,
    @SerializedName("picture") val picture : String
)
