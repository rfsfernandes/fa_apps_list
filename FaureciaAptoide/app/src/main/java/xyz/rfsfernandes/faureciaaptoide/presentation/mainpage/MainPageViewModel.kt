package xyz.rfsfernandes.faureciaaptoide.presentation.mainpage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import xyz.rfsfernandes.faureciaaptoide.R
import xyz.rfsfernandes.faureciaaptoide.data.util.Resource
import xyz.rfsfernandes.faureciaaptoide.domain.usecases.GetAppsListUseCase
import xyz.rfsfernandes.faureciaaptoide.presentation.mainpage.viewstate.MainPageViewState
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor(
    private val getAppsList: GetAppsListUseCase
) : ViewModel() {
    private val _pageState: MutableStateFlow<MainPageViewState> =
        MutableStateFlow(MainPageViewState.Loading(true))

    val pageState: StateFlow<MainPageViewState> = _pageState

    /**
     * Fetches app list
     */
    fun getAppsList() {
        viewModelScope.launch {
            _pageState.emit(MainPageViewState.Loading(true))
            getAppsList.invoke().collect {
                Timber.i("collect() -> $it")
                when (it) {
                    is Resource.Error -> {
                        _pageState.emit(MainPageViewState.Loading(false))
                        _pageState.emit(MainPageViewState.Error(R.string.generic_error_message))
                    }

                    is Resource.NetworkError -> {
                        _pageState.emit(MainPageViewState.Loading(false))
                        _pageState.emit(MainPageViewState.Error(R.string.network_error_message))
                    }

                    is Resource.Success -> _pageState.emit(MainPageViewState.ContentData(it.data))
                    else -> {}
                }
            }
        }
    }
}
