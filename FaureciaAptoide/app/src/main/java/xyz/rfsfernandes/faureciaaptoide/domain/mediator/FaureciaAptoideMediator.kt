package xyz.rfsfernandes.faureciaaptoide.domain.mediator

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import xyz.rfsfernandes.faureciaaptoide.data.datasource.Repository
import xyz.rfsfernandes.faureciaaptoide.data.util.Resource
import xyz.rfsfernandes.faureciaaptoide.domain.mappers.toDataModel
import xyz.rfsfernandes.faureciaaptoide.domain.mappers.toListDataModel
import xyz.rfsfernandes.faureciaaptoide.domain.model.AppInfoDataModel

/**
 * I normally use this Mediator in order to mediate the data from data layer to the domain layer
 * This means turning entities into DataModels that can be better suited for the domain that is using the data
 */
class FaureciaAptoideMediator(private val repository: Repository) {

    val appListResource: Flow<Resource<List<AppInfoDataModel>>> = repository.getApps().map {
        when (it) {
            is Resource.Error -> Resource.Error<List<AppInfoDataModel>>(it.message, it.errorCode)
            is Resource.NetworkError -> Resource.NetworkError<List<AppInfoDataModel>>(it.message.toString())
            is Resource.Success -> Resource.Success<List<AppInfoDataModel>>(it.data?.toListDataModel())
            is Resource.Default -> Resource.Default()
        }
    }.onEach { Timber.i("FaureciaAptoideMediator appListResource emitted: $it") }

    fun appInfoResource(id: Int): Flow<Resource<AppInfoDataModel>> =
        repository.getAppDetails(id).map {
            when (it) {
                is Resource.Error -> Resource.Error<AppInfoDataModel>(it.message, it.errorCode)
                is Resource.NetworkError -> Resource.NetworkError<AppInfoDataModel>(it.message.toString())
                is Resource.Success -> Resource.Success<AppInfoDataModel>(it.data?.toDataModel())
                is Resource.Default -> Resource.Default()
            }
        }.onEach { Timber.i("FaureciaAptoideMediator appInfoResource emitted: $it") }

}
