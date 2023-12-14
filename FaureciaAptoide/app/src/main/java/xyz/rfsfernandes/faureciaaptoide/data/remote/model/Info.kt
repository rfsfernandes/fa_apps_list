package xyz.rfsfernandes.faureciaaptoide.data.remote.model

import com.google.gson.annotations.SerializedName


data class Info(

    @SerializedName("status") var status: String? = null,
    @SerializedName("time") var time: Time? = Time()

)
