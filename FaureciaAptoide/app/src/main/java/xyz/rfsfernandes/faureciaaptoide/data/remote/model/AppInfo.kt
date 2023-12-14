package xyz.rfsfernandes.faureciaaptoide.data.remote.model

import com.google.gson.annotations.SerializedName


data class AppInfo(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("package") var packageName: String? = null,
    @SerializedName("store_id") var storeId: Int? = null,
    @SerializedName("store_name") var storeName: String? = null,
    @SerializedName("vername") var vername: String? = null,
    @SerializedName("vercode") var vercode: Int? = null,
    @SerializedName("md5sum") var md5sum: String? = null,
    @SerializedName("apk_tags") var apkTags: ArrayList<String> = arrayListOf(),
    @SerializedName("size") var size: Int? = null,
    @SerializedName("downloads") var downloads: Int? = null,
    @SerializedName("pdownloads") var pdownloads: Int? = null,
    @SerializedName("added") var added: String? = null,
    @SerializedName("modified") var modified: String? = null,
    @SerializedName("updated") var updated: String? = null,
    @SerializedName("rating") var rating: Double? = null,
    @SerializedName("icon") var icon: String? = null,
    @SerializedName("graphic") var graphic: String? = null,
    @SerializedName("uptype") var uptype: String? = null

)
