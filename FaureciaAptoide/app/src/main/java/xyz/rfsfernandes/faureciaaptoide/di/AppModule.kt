package xyz.rfsfernandes.faureciaaptoide.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.rfsfernandes.faureciaaptoide.BuildConfig
import xyz.rfsfernandes.faureciaaptoide.data.datasource.Repository
import xyz.rfsfernandes.faureciaaptoide.data.datasource.RepositoryImpl
import xyz.rfsfernandes.faureciaaptoide.data.local.AppDatabase
import xyz.rfsfernandes.faureciaaptoide.data.local.AptoideAppsDAO
import xyz.rfsfernandes.faureciaaptoide.data.remote.AptoideService
import xyz.rfsfernandes.faureciaaptoide.data.remote.RetrofitBuilder
import xyz.rfsfernandes.faureciaaptoide.domain.mediator.FaureciaAptoideMediator
import xyz.rfsfernandes.faureciaaptoide.domain.usecases.GetAppDetailsUseCase
import xyz.rfsfernandes.faureciaaptoide.domain.usecases.GetAppsListUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRepository(
        service: AptoideService,
        dao: AptoideAppsDAO,
    ): Repository {
        return RepositoryImpl(service, dao)
    }

    @Provides
    @Singleton
    fun provideMediator(repository: Repository): FaureciaAptoideMediator {
        return FaureciaAptoideMediator(repository)
    }

    @Provides
    @Singleton
    fun provideGetAppsList(mediator: FaureciaAptoideMediator): GetAppsListUseCase {
        return GetAppsListUseCase(mediator)
    }

    @Provides
    @Singleton
    fun provideGetAppDetails(mediator: FaureciaAptoideMediator): GetAppDetailsUseCase {
        return GetAppDetailsUseCase(mediator)
    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, BuildConfig.DB_NAME).build()
    }

    @Provides
    @Singleton
    fun provideService(): AptoideService {
        return RetrofitBuilder(BuildConfig.APTOIDE_ENDPOINT).aptoideService
    }

    @Provides
    @Singleton
    fun provideDAO(appDatabase: AppDatabase): AptoideAppsDAO {
        return appDatabase.aptoideAppsDAO
    }
}
