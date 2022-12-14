package com.elthobhy.nasatechport.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.elthobhy.nasatechport.DataDummy
import com.elthobhy.nasatechport.MainDispatcherRule
import com.elthobhy.nasatechport.main.TechportListAdapter
import com.elthobhy.nasatechport.core.core.data.TechportRepository
import com.elthobhy.nasatechport.getOrAwaitValue
import com.elthobhy.nasatechport.core.core.data.remote.QuoteResponseItem
import com.elthobhy.nasatechport.main.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var techportRepository: TechportRepository

    @Test
    fun `when Get Quote Should Not Null and Return Success`() = runTest {
        val dummyQuote = DataDummy.generateDummyQuoteResponse()
//        val data: PagingData<QuoteResponseItem> = PagingData.from(dummyQuote)
        val data: PagingData<QuoteResponseItem> = QuotePagingSource.snapshot(dummyQuote)
        val expectedQuote = MutableLiveData<PagingData<QuoteResponseItem>>()
        expectedQuote.value = data
        Mockito.`when`(techportRepository.getData()).thenReturn(expectedQuote)
        
        val mainViewModel = MainViewModel(techportRepository)
        val actualQuote: PagingData<QuoteResponseItem> = mainViewModel.techport.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = TechportListAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualQuote)

        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(dummyQuote, differ.snapshot())
        Assert.assertEquals(dummyQuote.size, differ.snapshot().size)
        Assert.assertEquals(dummyQuote[0].author, differ.snapshot()[0]?.author)
    }
}

class QuotePagingSource : PagingSource<Int, LiveData<List<QuoteResponseItem>>>() {
    companion object {
        fun snapshot(items: List<QuoteResponseItem>): PagingData<QuoteResponseItem> {
            return PagingData.from(items)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LiveData<List<QuoteResponseItem>>>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<QuoteResponseItem>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}