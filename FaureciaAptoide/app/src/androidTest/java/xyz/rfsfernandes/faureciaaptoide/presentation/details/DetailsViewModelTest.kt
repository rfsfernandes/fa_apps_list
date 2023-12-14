package xyz.rfsfernandes.faureciaaptoide.presentation.details

import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import xyz.rfsfernandes.faureciaaptoide.R
import xyz.rfsfernandes.faureciaaptoide.data.util.Resource
import xyz.rfsfernandes.faureciaaptoide.domain.model.AppInfoDataModel
import xyz.rfsfernandes.faureciaaptoide.domain.usecases.GetAppDetailsUseCase
import xyz.rfsfernandes.faureciaaptoide.presentation.details.viewstate.DetailsPageViewState
import xyz.rfsfernandes.faureciaaptoide.testutils.MainDispatcherRule

class DetailsViewModelTest {

    @get:Rule
    val dispatcher = MainDispatcherRule()

    private lateinit var viewModel: DetailsViewModel
    private lateinit var getAppDetailsUseCase: GetAppDetailsUseCase
    private var appInfoDataModel = MutableStateFlow<Resource<AppInfoDataModel>>(
        Resource.Default()
    )

    @Before
    fun setUp() {
        getAppDetailsUseCase = mockk<GetAppDetailsUseCase>()
        every { getAppDetailsUseCase.invoke(any()) } returns appInfoDataModel
        viewModel = DetailsViewModel(getAppDetailsUseCase)
    }

    @After
    fun tearDown() {
        clearAllMocks()
        runBlocking {
            appInfoDataModel.emit(Resource.Default())
        }
    }

    @Test
    fun stateIsLoadingBeforeEmitting() = runTest {
        assertEquals(DetailsPageViewState.Loading(true), viewModel.pageState.value)
    }

    @Test
    fun stateReturnsContentWhenSucessIsEmitted() = runTest {
        val testId = 234
        viewModel.getDetails(testId)
        val value = AppInfoDataModel(id = testId)
        appInfoDataModel.emit(
            Resource.Success(value)
        )
        val expected = DetailsPageViewState.ContentData(
            value
        )
        assertEquals(expected, viewModel.pageState.value)
    }

    @Test
    fun stateReturnsGenericErrorMessageWhenErrorIsEmitted() = runTest {
        val testId = 234
        viewModel.getDetails(testId)
        appInfoDataModel.emit(
            Resource.Error()
        )
        val expected = DetailsPageViewState.Error(R.string.generic_error_message)
        assertEquals(expected, viewModel.pageState.value)
    }

    @Test
    fun stateReturnsNetworkErrorMessageWhenNetworkErrorIsEmitted() = runTest {
        val testId = 234
        viewModel.getDetails(testId)
        appInfoDataModel.emit(
            Resource.NetworkError("")
        )
        val expected = DetailsPageViewState.Error(R.string.network_error_message)
        assertEquals(expected, viewModel.pageState.value)
    }
}