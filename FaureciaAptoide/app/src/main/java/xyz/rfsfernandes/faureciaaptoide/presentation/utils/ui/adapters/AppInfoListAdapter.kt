package xyz.rfsfernandes.faureciaaptoide.presentation.utils.ui.adapters

import android.view.LayoutInflater
import android.view.View.INVISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import xyz.rfsfernandes.faureciaaptoide.databinding.AppListItemBinding
import xyz.rfsfernandes.faureciaaptoide.domain.model.AppInfoDataModel
import xyz.rfsfernandes.faureciaaptoide.presentation.utils.ui.diffs.AppInfoDataModelDiffCallBack

class AppInfoListAdapter(private val onClick: (AppInfoDataModel) -> Unit) :
    ListAdapter<AppInfoDataModel, AppInfoListAdapter.AppInfoListViewHolder>(
        AppInfoDataModelDiffCallBack
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppInfoListViewHolder {
        val binding = AppListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AppInfoListViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: AppInfoListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class AppInfoListViewHolder(
        private val binding: AppListItemBinding, val
        onClick: (AppInfoDataModel) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var appInfo: AppInfoDataModel

        init {
            binding.root.setOnClickListener {
                if (::appInfo.isInitialized) {
                    onClick(appInfo)
                }
            }
        }

        fun bind(appInfoDataModel: AppInfoDataModel) = with(binding) {
            appInfo = appInfoDataModel
            with(appInfo) {
                rating?.let {
                    textViewAppRating.text = it.toString()
                } ?: run { textViewAppRating.visibility = INVISIBLE }
                appInfo.name?.let {
                    textViewAppTitle.text = it
                } ?: run { textViewAppTitle.visibility = INVISIBLE }
            }
            simpleDraweeViewGraphic.setImageURI(appInfo.icon.toString())
        }

    }
}