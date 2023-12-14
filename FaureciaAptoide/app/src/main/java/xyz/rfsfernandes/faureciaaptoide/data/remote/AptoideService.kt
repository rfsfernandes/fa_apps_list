package xyz.rfsfernandes.faureciaaptoide.data.remote

import retrofit2.Response
import retrofit2.http.GET
import xyz.rfsfernandes.faureciaaptoide.data.remote.model.ListAppsResponse

interface AptoideService {

    @GET("bulkRequest/api_list/listApps")
    suspend fun getAptoideAppsList(): Response<ListAppsResponse>
}