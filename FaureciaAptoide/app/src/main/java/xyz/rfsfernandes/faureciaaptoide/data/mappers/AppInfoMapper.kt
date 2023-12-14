package xyz.rfsfernandes.faureciaaptoide.data.mappers

import xyz.rfsfernandes.faureciaaptoide.data.local.model.AppInfoEntity
import xyz.rfsfernandes.faureciaaptoide.data.remote.model.AppInfo

fun AppInfo.toEntity(): AppInfoEntity {
    return AppInfoEntity(
        id = id,
        name = name,
        packageName = packageName,
        storeId = storeId,
        storeName = storeName,
        vername = vername,
        vercode = vercode,
        size = size,
        downloads = downloads,
        added = added,
        modified = modified,
        updated = updated,
        rating = rating,
        icon = icon,
        graphic = graphic,
        uptype = uptype,
    )
}
