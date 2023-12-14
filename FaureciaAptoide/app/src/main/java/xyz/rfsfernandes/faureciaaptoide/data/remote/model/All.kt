package xyz.rfsfernandes.faureciaaptoide.data.remote.model

import com.google.gson.annotations.SerializedName


data class All(

    @SerializedName("info") var info: Info? = Info(),
    @SerializedName("data") var data: Data? = Data()

)
