package xyz.rfsfernandes.faureciaaptoide.domain.model

data class AppInfoDataModel(
    var id: Int? = null,
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
