package xyz.rfsfernandes.faureciaaptoide.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import xyz.rfsfernandes.faureciaaptoide.data.util.Resource
import xyz.rfsfernandes.faureciaaptoide.domain.mediator.FaureciaAptoideMediator
import xyz.rfsfernandes.faureciaaptoide.domain.model.AppInfoDataModel

class GetAppDetailsUseCase(private val mediator: FaureciaAptoideMediator) {
    operator fun invoke(id: Int):
            Flow<Resource<AppInfoDataModel>> {
        return mediator.appInfoResource(id)
            .onEach { Timber.i("Datasource. GetAppDetailsUseCase returns $it") }
    }
}
