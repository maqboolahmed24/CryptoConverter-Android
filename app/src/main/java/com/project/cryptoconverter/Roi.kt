package com.example.example

import com.google.gson.annotations.SerializedName


data class Roi (

  @SerializedName("user_name" ) var userName : String? = null,
  @SerializedName("email"     ) var email    : String? = null,
  @SerializedName("name"      ) var name     : String? = null

)