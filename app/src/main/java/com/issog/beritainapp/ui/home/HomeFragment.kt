package com.issog.beritainapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.issog.beritainapp.databinding.FragmentHomeBinding
import com.issog.beritainapp.ui.home.adapter.HomeCategoryAdapter
import com.issog.beritainapp.ui.home.adapter.HomeSourceAdapter
import com.issog.beritainapp.utils.DataGenerator
import com.issog.core.domain.model.SourceModel
import com.issog.core.utils.ArchUtils.observe
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
        homeViewModel.getSources()
        observe(homeViewModel.sourceList, ::handleGetSourceList)
    }

    private fun handleGetSourceList(result: UiState<List<SourceModel>>) {
        Log.d("result",result.toString())
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
        }
    }

    private fun initViews() {
        initCategoryAdapters()
    }

    private fun initCategoryAdapters() {
        val categories = DataGenerator.generateCategoryData()
        val catAdapter = HomeCategoryAdapter()
        catAdapter.initData(categories)
        catAdapter.initClick { cat ->
            Toast.makeText(activity, cat.category, Toast.LENGTH_SHORT).show()
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
            Toast.makeText(activity, src.name, Toast.LENGTH_SHORT).show()
        }

        binding.rvSource.apply {
            layoutManager = GridLayoutManager(activity, 3)
            adapter = sourceAdapter
        }
    }
}