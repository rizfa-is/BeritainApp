package com.issog.beritainapp.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.issog.beritainapp.R
import com.issog.beritainapp.databinding.FragmentHomeBinding
import com.issog.beritainapp.ui.home.adapter.HomeCategoryAdapter
import com.issog.beritainapp.ui.home.adapter.HomeSourceAdapter
import com.issog.beritainapp.utils.DataGenerator
import com.issog.core.domain.model.SourceModel
import com.issog.core.utils.ArchUtils.observe
import com.issog.core.utils.NavigationUtils.safeNavigate
import com.issog.core.utils.UiState
import com.issog.core.utils.gone
import com.issog.core.utils.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        fetchData()
    }

    private fun fetchData() {
        observe(homeViewModel.sourceList, ::handleGetSourceList)
    }

    private fun handleGetSourceList(result: UiState<List<SourceModel>>?) {
        when(result) {
            is UiState.Loading -> {
                binding.pbSource.visible()
                binding.rvSource.gone()
            }
            is UiState.Error -> {
                binding.pbSource.gone()
                binding.rvSource.visible()
                Toast.makeText(activity, result.message, Toast.LENGTH_SHORT).show()
            }
            is UiState.NetworkError -> {
                binding.pbSource.gone()
                binding.rvSource.visible()
                Toast.makeText(activity, "Network Error!", Toast.LENGTH_SHORT).show()
            }
            is UiState.Success -> {
                binding.pbSource.gone()
                binding.rvSource.visible()
                initSourceAdapters(result.data)
            }

            else -> {}
        }
    }

    private fun initViews() {
        initCategoryAdapters()
        binding.ivFavorite.setOnClickListener {
            installFavoriteModule()
        }
    }

    private fun installFavoriteModule() {
        val splitInstallManager = activity?.let { SplitInstallManagerFactory.create(it) }
        val moduleName = "favorite"
        if (splitInstallManager?.installedModules?.contains(moduleName) == true) {
            moveToFavorite()
        } else {
            val request = SplitInstallRequest.newBuilder()
                .addModule(moduleName)
                .build()

            splitInstallManager?.startInstall(request)
                ?.addOnSuccessListener {
                    Toast.makeText(activity, "Success installing module", Toast.LENGTH_SHORT).show()
                    moveToFavorite()
                }
                ?.addOnFailureListener {
                    Toast.makeText(activity, "Error installing module", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun moveToFavorite() {
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("beritain://favorite")
                )
            )
        } catch (e: ClassNotFoundException) {
            Toast.makeText(activity, "Module Favorite Not Found", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun initCategoryAdapters() {
        val categories = DataGenerator.generateCategoryData()
        val catAdapter = HomeCategoryAdapter()
        catAdapter.initData(categories)
        catAdapter.initClick { cat ->
            findNavController().safeNavigate(R.id.newsFragment, bundleOf("category" to cat))
        }

        binding.rvCategory.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = catAdapter
        }
    }


    private fun initSourceAdapters(data: List<SourceModel>) {
        val sourceAdapter = HomeSourceAdapter()
        sourceAdapter.initData(data)
        sourceAdapter.initClick { src ->
            findNavController().safeNavigate(R.id.newsFragment, bundleOf("source" to src))
        }

        binding.rvSource.apply {
            layoutManager = GridLayoutManager(activity, 3)
            adapter = sourceAdapter
        }
    }
}