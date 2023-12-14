package xyz.rfsfernandes.faureciaaptoide.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import xyz.rfsfernandes.faureciaaptoide.R
import xyz.rfsfernandes.faureciaaptoide.data.util.Resource
import xyz.rfsfernandes.faureciaaptoide.domain.usecases.GetAppDetailsUseCase
import xyz.rfsfernandes.faureciaaptoide.presentation.details.viewstate.DetailsPageViewState
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    val getAppDetails: GetAppDetailsUseCase
) : ViewModel() {

    private val _pageState: MutableStateFlow<DetailsPageViewState> =
        MutableStateFlow(DetailsPageViewState.Loading(true))

    val pageState: StateFlow<DetailsPageViewState> = _pageState

    /**
     * Fetches app details by ID
     */
    fun getDetails(id: Int) {
        viewModelScope.launch {
            getAppDetails(id).collect {
                Timber.i("collect() -> $it")
                when (it) {
                    is Resource.Error -> {
                        _pageState.emit(DetailsPageViewState.Loading(false))
                        _pageState.emit(DetailsPageViewState.Error(R.string.generic_error_message))
                    }

                    is Resource.NetworkError -> {
                        _pageState.emit(DetailsPageViewState.Loading(false))
                        _pageState.emit(DetailsPageViewState.Error(R.string.network_error_message))
                    }

                    is Resource.Success -> _pageState.emit(DetailsPageViewState.ContentData(it.data))
                    else -> {}
                }
            }
        }
    }

}
