package xyz.rfsfernandes.faureciaaptoide.data.remote.model

import com.google.gson.annotations.SerializedName


data class Responses(

    @SerializedName("listApps") var listApps: ListApps? = ListApps()

)
