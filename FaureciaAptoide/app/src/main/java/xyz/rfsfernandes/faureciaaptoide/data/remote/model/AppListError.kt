package xyz.rfsfernandes.faureciaaptoide.data.remote.model

import com.google.gson.annotations.SerializedName

data class AppListError(
    @SerializedName("code") val code: String? = null,
    @SerializedName("description") val message: String? = null
)
