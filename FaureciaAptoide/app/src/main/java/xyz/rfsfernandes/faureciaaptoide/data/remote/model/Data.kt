package xyz.rfsfernandes.faureciaaptoide.data.remote.model

import com.google.gson.annotations.SerializedName


data class Data(

    @SerializedName("total") var total: Int? = null,
    @SerializedName("offset") var offset: Int? = null,
    @SerializedName("limit") var limit: Int? = null,
    @SerializedName("next") var next: Int? = null,
    @SerializedName("hidden") var hidden: Int? = null,
    @SerializedName("list") var appInfo: List<AppInfo> = listOf()

)
