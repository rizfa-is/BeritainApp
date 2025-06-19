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

class NewsFragment : Fragment(), NewsItemClickCallback {
    private lateinit var binding: FragmentNewsBinding
    private val newsViewModel: NewsViewModel by viewModel()
    private val newsAdapter by lazy { NewsAdapter() }
    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNewsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initNewsList()
    }

    private fun initViews() {
        binding.apply {
            val category = arguments?.getParcelable<ItemCategory>("category")
            val source = arguments?.getParcelable<SourceModel>("source")

            etSearch.addTextChangedListener { text: Editable? ->
                val txtSearch = text.toString()
                if (txtSearch.length > MINIMUM_SEARCH_LENGTH || txtSearch.isEmpty()) {
                    debounceSearchAction {
                        newsViewModel.getNews(
                            category?.category.orEmpty(),
                            source?.id.orEmpty(),
                            txtSearch,
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

                delay(DELAY_GET_NEWS)
                binding.pbNews.gone()
                binding.rvNews.visible()
                result?.let { newsAdapter.submitData(it) }
            }
        }
    }

    private fun initNewsList() {
        newsAdapter.initCallback(this)
        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter =
                newsAdapter.withLoadStateFooter(
                    footer =
                        NewsLoadingStateAdapter().also {
                            it.initClick { newsAdapter.retry() }
                        },
                )
        }
    }

    private fun debounceSearchAction(action: () -> Unit) {
        searchJob?.cancel()
        searchJob = null
        searchJob =
            lifecycleScope.launch {
                delay(DELAY_DEBOUNCE_SEARCH)
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
            bundleOf("news" to articleModel),
        )
    }

    override fun onSaveFavorite(articleModel: ArticleModel) {
        newsViewModel.addFavorite(articleModel)
    }

    override fun onDeleteFavorite(articleModel: ArticleModel) {
        newsViewModel.addFavorite(articleModel)
    }

    companion object {
        private const val DELAY_GET_NEWS = 500L
        private const val DELAY_DEBOUNCE_SEARCH = 1000L
        private const val MINIMUM_SEARCH_LENGTH = 6
    }
}
