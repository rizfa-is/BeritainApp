package com.issog.beritainapp.ui.news

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.issog.beritainapp.ui.news.adapter.DiffNewsCallback
import com.issog.beritainapp.utils.DummyData
import com.issog.beritainapp.utils.LiveDataUtils.getOrAwaitValue
import com.issog.beritainapp.utils.MainDispatcherRule
import com.issog.core.data.source.remote.request.NewsRequest
import com.issog.core.domain.model.ArticleModel
import com.issog.core.domain.usecase.BeritainUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var beritainUseCase: BeritainUseCase
    private lateinit var newsViewModel: NewsViewModel

    @Before
    fun setUp() {
        newsViewModel = NewsViewModel(beritainUseCase)
    }

    @Test
    fun `when Get News should not null and return Data`() = runTest {
        val dummyData = DummyData.newsDummyData()
        val data: PagingData<ArticleModel> = NewsPagingSource.snapshot(dummyData)
        val expectedNews = MutableLiveData<PagingData<ArticleModel>>()
        expectedNews.value = data
        Mockito.`when`(beritainUseCase.getTopHeadlineByCategory(NewsRequest())).thenReturn(expectedNews)

        val actualNews = newsViewModel.getNews("","").getOrAwaitValue()
        val differ = AsyncPagingDataDiffer(
            diffCallback = DiffNewsCallback(),
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualNews)

        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(dummyData.size, differ.snapshot().size)
        Assert.assertEquals(dummyData[0], differ.snapshot()[0])
    }

    fun `when Get News Empty should return No Data`() = runTest {
        val data: PagingData<ArticleModel> = NewsPagingSource.snapshot(emptyList())
        val expectedNews = MutableLiveData<PagingData<ArticleModel>>()
        expectedNews.value = data
        Mockito.`when`(beritainUseCase.getTopHeadlineByCategory(NewsRequest())).thenReturn(expectedNews)

        val actualNews = newsViewModel.getNews("","").getOrAwaitValue()
        val differ = AsyncPagingDataDiffer(
            diffCallback = DiffNewsCallback(),
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualNews)

        Assert.assertEquals(0, differ.snapshot().size)
    }

}

class NewsPagingSource : PagingSource<Int, LiveData<List<ArticleModel>>>() {
    companion object {
        fun snapshot(items: List<ArticleModel>): PagingData<ArticleModel> {
            return PagingData.from(items)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, LiveData<List<ArticleModel>>>): Int {
        return 0
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<ArticleModel>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}