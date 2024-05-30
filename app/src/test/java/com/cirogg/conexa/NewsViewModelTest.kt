package com.cirogg.conexa

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.room.InvalidationTracker
import com.cirogg.conexa.data.model.News
import com.cirogg.conexa.data.model.NewsDao
import com.cirogg.conexa.data.model.toEntity
import com.cirogg.conexa.data.remote.api.NewsApiService
import com.cirogg.conexa.data.repository.NewsRepository
import com.cirogg.conexa.viewmodel.NewsViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class NewsViewModelTest {

    private lateinit var viewModel: NewsViewModel
    private lateinit var newsRepository: NewsRepository
    private lateinit var newsDao: NewsDao
    private lateinit var newsApiService: NewsApiService

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        newsApiService = mockk()
        newsDao = mockk()
        newsRepository = mockk()
        viewModel = NewsViewModel(newsRepository)
        // Configura el comportamiento de las funciones suspendidas en NewsDao y NewsApiService
        coEvery { newsDao.insertAll(any()) } returns Unit
        coEvery { newsDao.getAllNews() } returns emptyList()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchNews calls newsRepository and updates newsList`() = runTest {
        val news = listOf(News("1", "Test News", "content", "", "", ""))

        // Configurar el mock para la llamada interna a getNews() en newsApiService
        coEvery { newsApiService.getNews() } returns news

        // Configurar el mock para la llamada a fetchNews() en newsRepository
        coEvery { newsRepository.fetchNews() } coAnswers {
            newsApiService.getNews().also {
                newsDao.insertAll(it.map { news -> news.toEntity() })
            }
        }

        viewModel.fetchNews()

        // Verificar que se llamó a fetchNews() en newsRepository
        coVerify { newsRepository.fetchNews() }

        // Verificar que se llamó a getNews() en newsApiService
        coVerify { newsApiService.getNews() }

        // Verificar que se llamó a insertAll() en newsDao
        coVerify { newsDao.insertAll(any()) }

        // Verificar que newsList se actualizó correctamente
        val actualNewsList = viewModel.newsList.first()
        assertEquals(news, actualNewsList)
    }

    @Test
    fun `setSearchQuery updates filteredNewsList`() = runTest {
        val news = listOf(
            News("1", "Kotlin News", "Content about Kotlin", "", "", ""),
            News("2", "Java News", "Content about Java", "", "", "")
        )
        viewModel.setNewsList(news)
        viewModel.setSearchQuery("Kotlin")

        val filteredNews = viewModel.filteredNewsList.first()
        assertEquals(listOf(news[0]), filteredNews)
    }
}