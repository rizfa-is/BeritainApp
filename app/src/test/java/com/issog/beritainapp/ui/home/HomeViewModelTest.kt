package com.issog.beritainapp.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.issog.beritainapp.utils.DummyData
import com.issog.beritainapp.utils.LiveDataUtils.getOrAwaitValue
import com.issog.core.domain.model.SourceModel
import com.issog.core.domain.usecase.BeritainUseCase
import com.issog.core.utils.UiState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var beritainUseCase: BeritainUseCase
    private lateinit var homeViewModel: HomeViewModel
    private val dummyData = DummyData.sourceDummyData()

    @Before
    fun setUp() {
        homeViewModel = HomeViewModel(beritainUseCase)
    }

    @Test
    fun `when Get Sources should not null and return UiState Success`() {
        val expected = MutableLiveData<UiState<List<SourceModel>>>()
        expected.value = UiState.Success(dummyData)
        `when`(beritainUseCase.getNewsSources()).thenReturn(expected)

        val actualResult = homeViewModel.sourceList.getOrAwaitValue()
        assertNotNull(actualResult)
        assertTrue(actualResult == expected.value)
        assertEquals(dummyData.size, (actualResult as UiState.Success).data.size)
    }

    @Test
    fun `when Network Error should return UiState Network Error`() {
        val expected = MutableLiveData<UiState<List<SourceModel>>>()
        expected.value = UiState.NetworkError
        `when`(beritainUseCase.getNewsSources()).thenReturn(expected)

        val actualResult = homeViewModel.sourceList.getOrAwaitValue()
        assertNotNull(actualResult)
        assertTrue(actualResult == expected.value)
    }
}
