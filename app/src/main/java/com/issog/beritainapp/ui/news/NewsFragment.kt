package com.issog.beritainapp.ui.news

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.issog.beritainapp.R
import com.issog.beritainapp.databinding.FragmentNewsBinding
import com.issog.beritainapp.ui.home.model.ItemCategory
import com.issog.beritainapp.ui.news.adapter.NewsAdapter
import com.issog.beritainapp.ui.news.adapter.NewsLoadingStateAdapter
import com.issog.core.domain.model.ArticleModel
import com.issog.core.domain.model.SourceModel
import com.issog.core.utils.ArchUtils.observe
import com.issog.core.utils.NavigationUtils.safeNavigate
import com.issog.core.utils.gone
import com.issog.core.utils.visible
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding
    private val newsViewModel: NewsViewModel by viewModel()
    private val newsAdapter by lazy { NewsAdapter() }
    private var searchJob: Job? = null

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
                    debounceSearchAction {
                        newsViewModel.getNews(
                            category?.category.orEmpty(),
                            source?.id.orEmpty(),
                            txtSearch
                        ).handleGetNews()
                    }
                }
            }

            newsViewModel.getNews(category?.category.orEmpty(), source?.id.orEmpty()).handleGetNews()
        }
    }

    private fun LiveData<PagingData<ArticleModel>>.handleGetNews() {
        observe(this) { result ->
            lifecycleScope.launch {
                binding.pbNews.visible()
                binding.rvNews.gone()

                delay(500)
                binding.pbNews.gone()
                binding.rvNews.visible()
                result?.let { initNewsList(it) }
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
            adapter = newsAdapter.withLoadStateFooter(
                footer = NewsLoadingStateAdapter().also {
                    it.initClick { newsAdapter.retry() }
                }
            )
        }
        newsAdapter.submitData(data)
    }

    private fun debounceSearchAction(action: () -> Unit) {
        searchJob?.cancel()
        searchJob = null
        searchJob = lifecycleScope.launch {
            delay(1000)
            action.invoke()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        searchJob?.cancel()
        searchJob = null
    }
}