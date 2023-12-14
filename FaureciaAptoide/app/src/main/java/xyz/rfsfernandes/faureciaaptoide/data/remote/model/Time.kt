package xyz.rfsfernandes.faureciaaptoide.data.remote.model

import com.google.gson.annotations.SerializedName


data class Time(

    @SerializedName("seconds") var seconds: Double? = null,
    @SerializedName("human") var human: String? = null

)
