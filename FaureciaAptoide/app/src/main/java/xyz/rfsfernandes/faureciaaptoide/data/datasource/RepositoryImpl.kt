package xyz.rfsfernandes.faureciaaptoide.data.datasource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import xyz.rfsfernandes.faureciaaptoide.data.local.AptoideAppsDAO
import xyz.rfsfernandes.faureciaaptoide.data.local.model.AppInfoEntity
import xyz.rfsfernandes.faureciaaptoide.data.mappers.toEntity
import xyz.rfsfernandes.faureciaaptoide.data.remote.AptoideService
import xyz.rfsfernandes.faureciaaptoide.data.util.Resource

class RepositoryImpl(
    private val aptoideService: AptoideService,
    private val aptoideAppsDAO: AptoideAppsDAO,
) : Repository {

    override fun getApps(): Flow<Resource<List<AppInfoEntity>>> = flow {
        val dbResult = aptoideAppsDAO.getAppList() // Fetches cached apps
        if (!dbResult.isNullOrEmpty()) { // checks if there are any cached apps
            emit(Resource.Success(dbResult)) // if yes, emits the cached apps
            Timber.i("Emitted saved apps: $dbResult")
        }
        /*
        Even if it emits cached apps, I believe that a call should be done anyway, so we have
        the most up to date list as possible
        */

        try {
            val networkResult = aptoideService.getAptoideAppsList()
            if (networkResult.isSuccessful) {
                networkResult.body()?.responses?.listApps?.datasets?.all?.data?.appInfo?.let {
                    // Opted to order by downloads as if it was a list of the most popular
                    val mapRequestBody = it.map { item -> item.toEntity() }
                        .sortedByDescending { item -> item.downloads }
                    if (mapRequestBody != dbResult) { // If lists are different then we must update the database with new ones
                        deleteCachedApps() // Deletes cached app list
                        aptoideAppsDAO.insertAppsInfo(mapRequestBody) // inserts new apps into cache
                        emit(Resource.Success(mapRequestBody)) // Emits the new list
                        Timber.i("Saved new applist: $mapRequestBody")
                    }
                }
            } else {
                emit(
                    Resource.Error<List<AppInfoEntity>>(
                        message = networkResult.body()?.errors?.first()?.message,
                        networkResult.code()
                    )
                )
                Timber.i("Error while fetching")
            }
        } catch (e: Exception) {
            Timber.e("Exception while fetching $e")
            emit(Resource.NetworkError<List<AppInfoEntity>>(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    override fun getAppDetails(id: Int): Flow<Resource<AppInfoEntity>> = flow {
        aptoideAppsDAO.getAppInfoById(id)?.let {
            emit(Resource.Success(it))
            Timber.i("Emitted saved app info: $it")
        } ?: run {
            Timber.i("No app data found")
            emit(Resource.Error<AppInfoEntity>())
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun deleteCachedApps() {
        aptoideAppsDAO.cleanCache()
    }
}