package xyz.rfsfernandes.faureciaaptoide.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import xyz.rfsfernandes.faureciaaptoide.data.local.model.AppInfoEntity

@Dao
interface AptoideAppsDAO {

    // Opted to order by downloads as if it was a list of the most popular
    @Query("SELECT * FROM AppInfoEntity ORDER BY downloads DESC")
    fun getAppList(): List<AppInfoEntity>?

    @Query("SELECT * FROM AppInfoEntity WHERE id = :appId")
    suspend fun getAppInfoById(appId: Int): AppInfoEntity?

    @Query("DELETE FROM AppInfoEntity")
    suspend fun cleanCache()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppsInfo(list: List<AppInfoEntity>)
}
