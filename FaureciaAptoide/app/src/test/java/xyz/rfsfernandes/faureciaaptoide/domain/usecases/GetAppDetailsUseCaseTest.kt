package xyz.rfsfernandes.faureciaaptoide.domain.usecases

import app.cash.turbine.test
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import xyz.rfsfernandes.faureciaaptoide.data.datasource.RepositoryImpl
import xyz.rfsfernandes.faureciaaptoide.data.local.model.AppInfoEntity
import xyz.rfsfernandes.faureciaaptoide.data.util.Resource
import xyz.rfsfernandes.faureciaaptoide.domain.mediator.FaureciaAptoideMediator
import xyz.rfsfernandes.faureciaaptoide.testutils.MainDispatcherRule

class GetAppDetailsUseCaseTest {

    @get:Rule
    val dispatcher = MainDispatcherRule()

    private lateinit var getAppsDetailsUseCase: GetAppDetailsUseCase

    private val stateFlow =
        MutableStateFlow<Resource<AppInfoEntity>>(Resource.Default<AppInfoEntity>())

    @Before
    fun setUp() {
        val repositoryImpl: RepositoryImpl = mockk()

        every { repositoryImpl.getAppDetails(any()) } returns stateFlow
        every { repositoryImpl.getApps() } returns mockk()
        getAppsDetailsUseCase = GetAppDetailsUseCase(FaureciaAptoideMediator(repositoryImpl))
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun testSuccessCase() = runTest {
        val testId = 245
        stateFlow.emit(Resource.Success(AppInfoEntity(id = testId)))
        getAppsDetailsUseCase(testId).test {
            val actual = expectMostRecentItem()
            Assert.assertEquals(Resource.Success::class, actual::class)
            Assert.assertEquals(testId, actual.data?.id)
        }
    }

    @Test
    fun testErrorCase() = runTest {
        val testId = 245
        stateFlow.emit(Resource.Error())
        getAppsDetailsUseCase(testId).test {
            val actual = expectMostRecentItem()
            Assert.assertEquals(Resource.Error::class, actual::class)
        }
    }

    @Test
    fun testNetworkErrorCase() = runTest {
        val testId = 245
        val errorMessage = "Error"
        stateFlow.emit(Resource.NetworkError(errorMessage))
        getAppsDetailsUseCase(testId).test {
            val actual = expectMostRecentItem()
            Assert.assertEquals(Resource.NetworkError::class, actual::class)
            Assert.assertEquals(errorMessage, actual.message)
        }
    }
}