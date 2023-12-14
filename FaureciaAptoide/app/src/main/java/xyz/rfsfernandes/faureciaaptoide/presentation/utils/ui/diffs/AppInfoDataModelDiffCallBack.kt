package xyz.rfsfernandes.faureciaaptoide.presentation.utils.ui.diffs

import androidx.recyclerview.widget.DiffUtil
import xyz.rfsfernandes.faureciaaptoide.domain.model.AppInfoDataModel

object AppInfoDataModelDiffCallBack : DiffUtil.ItemCallback<AppInfoDataModel>() {
    override fun areItemsTheSame(oldItem: AppInfoDataModel, newItem: AppInfoDataModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: AppInfoDataModel, newItem: AppInfoDataModel): Boolean {
        return oldItem.id == newItem.id
    }
}