package xyz.rfsfernandes.faureciaaptoide.presentation.details.viewstate

import xyz.rfsfernandes.faureciaaptoide.domain.model.AppInfoDataModel

sealed class DetailsPageViewState {

    data class Loading(val isLoading: Boolean) : DetailsPageViewState()

    data class Error(
        val errorStringId: Int
    ) : DetailsPageViewState()

    data class ContentData(
        val appInfoList: AppInfoDataModel?
    ) : DetailsPageViewState()
}
