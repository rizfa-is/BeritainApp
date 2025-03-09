package com.issog.beritainapp.ui.favorite

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.issog.beritainapp.R
import com.issog.beritainapp.databinding.FragmentFavoriteBinding
import com.issog.beritainapp.ui.favorite.adapter.NewsFavoriteAdapter
import com.issog.core.domain.model.ArticleModel
import com.issog.core.utils.ArchUtils.observe
import com.issog.core.utils.NavigationUtils.safeNavigate
import com.issog.core.utils.UiState
import com.issog.core.utils.gone
import com.issog.core.utils.visible
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment(), FavoriteClickCallback {
    private lateinit var binding: FragmentFavoriteBinding
    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private val favoriteAdapter: NewsFavoriteAdapter by lazy { NewsFavoriteAdapter() }
    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =  FragmentFavoriteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initFavoriteList()
    }

    private fun initViews() {
        binding.apply {
            etSearch.addTextChangedListener { text: Editable? ->
                val txtSearch = text.toString()
                if (txtSearch.length > 3 || txtSearch.isEmpty()) {
                    debounceSearchAction {
                        favoriteViewModel.getFavoriteNews(txtSearch).handleGetNews()
                    }
                }
            }

            favoriteViewModel.getFavoriteNews().handleGetNews()
        }
    }

    private fun LiveData<UiState<List<ArticleModel>>>.handleGetNews() {
        observe(this) {
            it?.let { result ->
                when(result) {
                    is UiState.Loading -> {
                        binding.pbFavorite.visible()
                        binding.rvFavorite.gone()
                        binding.emptyState.gone()
                    }
                    is UiState.Error -> {
                        binding.pbFavorite.gone()
                        binding.rvFavorite.visible()
                        Toast.makeText(activity, result.message, Toast.LENGTH_SHORT).show()
                    }
                    is UiState.NetworkError -> {
                        binding.pbFavorite.gone()
                        binding.rvFavorite.visible()
                        Toast.makeText(activity, "Network Error!", Toast.LENGTH_SHORT).show()
                    }
                    is UiState.Success -> {
                        if (result.data.isEmpty()) {
                            binding.pbFavorite.gone()
                            binding.rvFavorite.gone()
                            binding.emptyState.visible()
                        } else {
                            binding.pbFavorite.gone()
                            binding.rvFavorite.visible()
                            favoriteAdapter.saveData(result.data)
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    private fun initFavoriteList() {
        favoriteAdapter.initCallback(this)
        binding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = favoriteAdapter
        }
    }

    private fun debounceSearchAction(action: () -> Unit) {
        searchJob?.cancel()
        searchJob = null
        searchJob = lifecycleScope.launch {
            delay(100)
            action.invoke()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        searchJob?.cancel()
        searchJob = null
    }

    override fun onNewsClick(articleModel: ArticleModel) {
        findNavController().safeNavigate(
            R.id.newsDetailFragment,
            bundleOf("url" to articleModel.url)
        )
    }

    override fun onDeleteFavorite(articleModel: ArticleModel) {
        favoriteViewModel.deleteFavorite(articleModel)
    }
}