package xyz.rfsfernandes.faureciaaptoide.presentation.details

import android.app.AlertDialog
import android.os.Bundle
import android.text.format.Formatter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import xyz.rfsfernandes.faureciaaptoide.R
import xyz.rfsfernandes.faureciaaptoide.databinding.FragmentDetailsBinding
import xyz.rfsfernandes.faureciaaptoide.domain.model.AppInfoDataModel
import xyz.rfsfernandes.faureciaaptoide.presentation.details.viewstate.DetailsPageViewState
import xyz.rfsfernandes.faureciaaptoide.presentation.utils.extensions.formatDate
import xyz.rfsfernandes.faureciaaptoide.presentation.utils.extensions.toSmallNumber

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModels()
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: DetailsFragmentArgs by navArgs()
    private val alertDialog by lazy {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.we_re_sorry))
            .setMessage(getString(R.string.download_is_not_available_in_demo_mode))
            .setNeutralButton(getString(R.string.close)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private lateinit var snackbar: Snackbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(layoutInflater)
        setupViews()
        viewModel.getDetails(args.aptoideAppId)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }

    /**
     * Initializes the collection of states
     */
    private fun initViewModel() = with(binding) {
        viewModel.pageState.onEach {
            when (it) {
                is DetailsPageViewState.ContentData -> {
                    setupViews(it.appInfoList)
                }

                is DetailsPageViewState.Error -> {
                    if (!::snackbar.isInitialized) {
                        snackbar = Snackbar.make(
                            binding.root, getString(it.errorStringId),
                            Toast.LENGTH_LONG
                        )
                    }
                    if (!snackbar.isShown) {
                        snackbar.show()
                    }
                }

                is DetailsPageViewState.Loading -> {
                    progressBar.isVisible = it.isLoading
                }
            }
        }.launchIn(lifecycleScope)
    }

    /**
     * Used to setupviews with content
     */
    private fun setupViews(appInfoDataModel: AppInfoDataModel? = null) = with(binding) {
        imageButtonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        buttonDownload.setOnClickListener {
            if (!alertDialog.isShowing) {
                alertDialog.show()
            }
        }

        appInfoDataModel?.let { appInfo ->
            appInfo.name?.let {
                textViewAppName.text = it
            } ?: run { textViewAppName.visibility = View.GONE }
            appInfo.rating?.let {
                textViewRating.text = it.toString()
            } ?: run { textViewRating.visibility = View.GONE }
            appInfo.downloads?.let {
                textViewDownloads.text = it.toSmallNumber()
            } ?: run { textViewDownloads.visibility = View.GONE }
            appInfo.storeName?.let {
                textViewStoreName.text = it
            } ?: run { textViewStoreName.visibility = View.GONE }
            appInfo.size?.let {
                textViewAppSize.text = Formatter.formatShortFileSize(requireActivity(), it.toLong())
            } ?: run { textViewAppSize.visibility = View.GONE }
            simpleDraweeView.setImageURI(appInfo.graphic.toString())
            simpleDraweeViewAppIcon.setImageURI(appInfo.icon.toString())
            appInfo.vername?.let {
                textViewVersion.text = it
            } ?: run { textViewVersion.visibility = View.GONE }
            appInfo.added?.let {
                textViewDateAdded.text = it.formatDate()
            } ?: run { textViewDateAdded.visibility = View.GONE }
            appInfo.updated?.let {
                textViewDateUpdated.text = it.formatDate()
            } ?: run { textViewDateUpdated.visibility = View.GONE }
            progressBar.isVisible = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
