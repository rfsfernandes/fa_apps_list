package xyz.rfsfernandes.faureciaaptoide.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import xyz.rfsfernandes.faureciaaptoide.data.util.Resource
import xyz.rfsfernandes.faureciaaptoide.domain.mediator.FaureciaAptoideMediator
import xyz.rfsfernandes.faureciaaptoide.domain.model.AppInfoDataModel

class GetAppsListUseCase(private val mediator: FaureciaAptoideMediator) {
    operator fun invoke():
            Flow<Resource<List<AppInfoDataModel>>> {
        return mediator.appListResource
            .onEach { Timber.i("Datasource. GetAppsListUseCase returns $it") }
    }
}
