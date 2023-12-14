package xyz.rfsfernandes.faureciaaptoide.presentation.mainpage

import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import xyz.rfsfernandes.faureciaaptoide.R
import xyz.rfsfernandes.faureciaaptoide.data.util.Resource
import xyz.rfsfernandes.faureciaaptoide.domain.model.AppInfoDataModel
import xyz.rfsfernandes.faureciaaptoide.domain.usecases.GetAppsListUseCase
import xyz.rfsfernandes.faureciaaptoide.presentation.mainpage.viewstate.MainPageViewState
import xyz.rfsfernandes.faureciaaptoide.testutils.MainDispatcherRule

class MainPageViewModelTest {

    @get:Rule
    val dispatcher = MainDispatcherRule()

    private lateinit var viewModel: MainPageViewModel
    private lateinit var getAppsListUseCase: GetAppsListUseCase

    private var appInfoDataModel = MutableStateFlow<Resource<List<AppInfoDataModel>>>(
        Resource.Default()
    )

    @Before
    fun setUp() {
        getAppsListUseCase = mockk<GetAppsListUseCase>()
        every { getAppsListUseCase.invoke() } returns appInfoDataModel
        viewModel = MainPageViewModel(getAppsListUseCase)
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
        Assert.assertEquals(MainPageViewState.Loading(true), viewModel.pageState.value)
    }

    @Test
    fun stateReturnsContentWhenSucessIsEmitted() = runTest {
        viewModel.getAppsList()
        val value = AppInfoDataModel(id = 234)
        appInfoDataModel.emit(
            Resource.Success(listOf(value))
        )
        val expected = MainPageViewState.ContentData(
            listOf(value)
        )
        Assert.assertEquals(expected, viewModel.pageState.value)
    }

    @Test
    fun stateReturnsGenericErrorMessageWhenErrorIsEmitted() = runTest {
        viewModel.getAppsList()
        appInfoDataModel.emit(
            Resource.Error()
        )
        val expected = MainPageViewState.Error(R.string.generic_error_message)
        Assert.assertEquals(expected, viewModel.pageState.value)
    }

    @Test
    fun stateReturnsNetworkErrorMessageWhenNetworkErrorIsEmitted() = runTest {
        viewModel.getAppsList()
        appInfoDataModel.emit(
            Resource.NetworkError("")
        )
        val expected = MainPageViewState.Error(R.string.network_error_message)
        Assert.assertEquals(expected, viewModel.pageState.value)
    }

}