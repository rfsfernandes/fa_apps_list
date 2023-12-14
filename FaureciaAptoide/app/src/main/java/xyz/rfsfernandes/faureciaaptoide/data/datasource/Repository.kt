package xyz.rfsfernandes.faureciaaptoide.data.datasource

import kotlinx.coroutines.flow.Flow
import xyz.rfsfernandes.faureciaaptoide.data.local.model.AppInfoEntity
import xyz.rfsfernandes.faureciaaptoide.data.util.Resource


interface Repository {
    fun getApps(): Flow<Resource<List<AppInfoEntity>>>
    fun getAppDetails(id: Int): Flow<Resource<AppInfoEntity>>
    suspend fun deleteCachedApps()
}
