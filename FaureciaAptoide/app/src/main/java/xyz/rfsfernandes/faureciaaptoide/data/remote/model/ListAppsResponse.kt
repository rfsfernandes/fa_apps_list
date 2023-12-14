package xyz.rfsfernandes.faureciaaptoide.data.remote.model

import com.google.gson.annotations.SerializedName


data class ListAppsResponse(

    @SerializedName("status") var status: String? = null,
    @SerializedName("responses") var responses: Responses? = Responses(),
    @SerializedName("errors") var errors: List<AppListError>? = listOf(),
)
