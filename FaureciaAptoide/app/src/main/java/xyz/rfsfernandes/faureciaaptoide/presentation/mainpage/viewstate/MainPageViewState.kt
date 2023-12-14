package xyz.rfsfernandes.faureciaaptoide.presentation.mainpage.viewstate

import xyz.rfsfernandes.faureciaaptoide.domain.model.AppInfoDataModel

sealed class MainPageViewState {

    data class Loading(val isLoading: Boolean) : MainPageViewState()

    data class Error(
        val errorStringId: Int
    ) : MainPageViewState()

    data class ContentData(
        val appInfoList: List<AppInfoDataModel>?
    ) : MainPageViewState()
}
