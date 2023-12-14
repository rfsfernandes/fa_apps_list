package xyz.rfsfernandes.faureciaaptoide.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import xyz.rfsfernandes.faureciaaptoide.data.local.model.AppInfoEntity

@Database(entities = [AppInfoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val aptoideAppsDAO: AptoideAppsDAO
}
