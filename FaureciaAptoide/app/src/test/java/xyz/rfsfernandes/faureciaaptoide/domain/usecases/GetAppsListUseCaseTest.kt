package xyz.rfsfernandes.faureciaaptoide.domain.usecases

import app.cash.turbine.test
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import xyz.rfsfernandes.faureciaaptoide.data.datasource.RepositoryImpl
import xyz.rfsfernandes.faureciaaptoide.data.local.model.AppInfoEntity
import xyz.rfsfernandes.faureciaaptoide.data.util.Resource
import xyz.rfsfernandes.faureciaaptoide.domain.mappers.toListDataModel
import xyz.rfsfernandes.faureciaaptoide.domain.mediator.FaureciaAptoideMediator
import xyz.rfsfernandes.faureciaaptoide.testutils.MainDispatcherRule

class GetAppsListUseCaseTest {

    @get:Rule
    val dispatcher = MainDispatcherRule()

    private lateinit var getAppsListUseCase: GetAppsListUseCase

    private val stateFlow =
        MutableStateFlow<Resource<List<AppInfoEntity>>>(Resource.Default<List<AppInfoEntity>>())

    @Before
    fun setUp() {
        val repositoryImpl: RepositoryImpl = mockk()

        every { repositoryImpl.getAppDetails(any()) } returns mockk()
        every { repositoryImpl.getApps() } returns stateFlow
        getAppsListUseCase = GetAppsListUseCase(FaureciaAptoideMediator(repositoryImpl))
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun testSuccessCase() = runTest {
        val testList = mutableListOf<AppInfoEntity>()
        (1..100).forEach {
            testList.add(AppInfoEntity(id = it))
        }
        stateFlow.emit(Resource.Success(testList))
        getAppsListUseCase().test {
            val actual = expectMostRecentItem()
            assertEquals(Resource.Success::class, actual::class)
            assertEquals(testList.toListDataModel(), actual.data)
        }
    }

    @Test
    fun testErrorCase() = runTest {
        stateFlow.emit(Resource.Error())
        getAppsListUseCase().test {
            val actual = expectMostRecentItem()
            assertEquals(Resource.Error::class, actual::class)
        }
    }

    @Test
    fun testNetworkErrorCase() = runTest {
        val errorMessage = "Error"
        stateFlow.emit(Resource.NetworkError(errorMessage))
        getAppsListUseCase().test {
            val actual = expectMostRecentItem()
            assertEquals(Resource.NetworkError::class, actual::class)
            assertEquals(errorMessage, actual.message)
        }
    }
}
