package xyz.rfsfernandes.faureciaaptoide.presentation.mainpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast.LENGTH_LONG
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import xyz.rfsfernandes.faureciaaptoide.data.worker.NewAppsAvailableWorkerWrapper
import xyz.rfsfernandes.faureciaaptoide.databinding.FragmentMainPageBinding
import xyz.rfsfernandes.faureciaaptoide.presentation.mainpage.viewstate.MainPageViewState
import xyz.rfsfernandes.faureciaaptoide.presentation.utils.ui.adapters.AppInfoListAdapter

@AndroidEntryPoint
class MainPageFragment : Fragment() {

    private val viewModel: MainPageViewModel by viewModels()
    private var _binding: FragmentMainPageBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: AppInfoListAdapter
    private lateinit var snackbar: Snackbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainPageBinding.inflate(layoutInflater)
        setupViews()
        viewModel.getAppsList()
        return binding.root
    }

    private fun setupViews() = with(binding) {
        adapter = AppInfoListAdapter {
            it.id?.let { id ->
                val directions =
                    MainPageFragmentDirections.actionMainPageFragmentToDetailsFragment(id)
                findNavController().navigate(directions)
            }
        }
        val gridLayoutManager = GridLayoutManager(requireContext(), 3)
        recyclerViewAppList.layoutManager = gridLayoutManager
        recyclerViewAppList.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        initViewModel()
    }

    private fun initViewModel() = with(binding) {
        viewModel.pageState.onEach {
            when (it) {
                is MainPageViewState.ContentData -> {
                    adapter.submitList(it.appInfoList) {
                        progressBar.isVisible = false
                    }
                }

                is MainPageViewState.Error -> {
                    if (!::snackbar.isInitialized) {
                        snackbar =
                            Snackbar.make(binding.root, getString(it.errorStringId), LENGTH_LONG)
                    }
                    if (!snackbar.isShown) {
                        snackbar.show()
                    }
                }

                is MainPageViewState.Loading -> {
                    progressBar.isVisible = it.isLoading
                }
            }
        }.launchIn(lifecycleScope)

        WorkManager.getInstance(requireActivity().applicationContext)
            .getWorkInfoByIdFlow(NewAppsAvailableWorkerWrapper.periodicWorkRequest.id).onEach {
                // Checks if work as succeeded which means a notification was sent to the user
                if (it != null && it.state == WorkInfo.State.SUCCEEDED) {
                    viewModel.getAppsList()
                }
            }.launchIn(lifecycleScope)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
