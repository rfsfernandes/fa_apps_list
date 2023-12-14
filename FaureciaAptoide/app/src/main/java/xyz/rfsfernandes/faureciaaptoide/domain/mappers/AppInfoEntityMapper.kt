package xyz.rfsfernandes.faureciaaptoide.domain.mappers

import xyz.rfsfernandes.faureciaaptoide.data.local.model.AppInfoEntity
import xyz.rfsfernandes.faureciaaptoide.domain.model.AppInfoDataModel

fun AppInfoEntity.toDataModel(): AppInfoDataModel {
    return AppInfoDataModel(
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
    )
}

fun List<AppInfoEntity>.toListDataModel(): List<AppInfoDataModel> {
    return this.map { it.toDataModel() }
}
