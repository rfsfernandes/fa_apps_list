package xyz.rfsfernandes.faureciaaptoide.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AppInfoEntity(
    @PrimaryKey var id: Int? = null,
    var name: String? = null,
    var packageName: String? = null,
    var storeId: Int? = null,
    var storeName: String? = null,
    var vername: String? = null,
    var vercode: Int? = null,
    var size: Int? = null,
    var downloads: Int? = null,
    var added: String? = null,
    var modified: String? = null,
    var updated: String? = null,
    var rating: Double? = null,
    var icon: String? = null,
    var graphic: String? = null,
    var uptype: String? = null
)
