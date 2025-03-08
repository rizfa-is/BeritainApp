package com.issog.beritainapp.ui.news

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.issog.beritainapp.R
import com.issog.beritainapp.databinding.FragmentNewsBinding
import com.issog.beritainapp.ui.home.model.ItemCategory
import com.issog.beritainapp.ui.news.adapter.NewsAdapter
import com.issog.core.domain.model.ArticleModel
import com.issog.core.domain.model.SourceModel
import com.issog.core.utils.NavigationUtils.safeNavigate
import com.issog.core.utils.UiState
import com.issog.core.utils.gone
import com.issog.core.utils.visible
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding
    private val newsViewModel: NewsViewModel by viewModel()
    private val newsAdapter by lazy { NewsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            val category = arguments?.getParcelable<ItemCategory>("category")
            val source = arguments?.getParcelable<SourceModel>("source")

            etSearch.addTextChangedListener { text: Editable? ->
                val txtSearch = text.toString()

                if (txtSearch.length > 6 || txtSearch.isEmpty()) {
                    newsViewModel.getNews(
                        category?.category.orEmpty(),
                        source?.id.orEmpty(),
                        txtSearch
                    )
                }
            }

            newsViewModel.getNews(category?.category.orEmpty(), source?.id.orEmpty())
            handleGetNews()
        }
    }

    private fun handleGetNews() {
        lifecycleScope.launch {
            newsViewModel.newsList.collectLatest { result ->
                when(result) {
                    is UiState.Loading -> {
                        binding.pbNews.visible()
                        binding.rvNews.gone()
                    }
                    is UiState.Success -> {
                        delay(500)
                        binding.pbNews.gone()
                        binding.rvNews.visible()
                        initNewsList(result.data)
                    }
                    else -> {}
                }
            }
        }
    }

    private suspend fun initNewsList(data: PagingData<ArticleModel>) {
        newsAdapter.initClick {
            findNavController().safeNavigate(
                R.id.newsDetailFragment,
                bundleOf("url" to it?.url.orEmpty())
            )
        }
        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = newsAdapter
        }
        newsAdapter.submitData(data)
    }
}