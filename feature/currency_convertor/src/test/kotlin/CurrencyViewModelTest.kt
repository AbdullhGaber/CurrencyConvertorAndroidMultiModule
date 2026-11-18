import android.content.SharedPreferences
import com.iti.core.data.worker.WorkManagerSyncManager
import com.iti.currency_convertor.CurrencyViewModel
import com.iti.testing.repository.TestCurrencyRepository
import com.iti.testing.worker.TestWorkManagerSyncManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import model.currency_convertor.ExchangeRates
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class CurrencyViewModelTest {
    lateinit var viewModel : CurrencyViewModel
    private lateinit var currencyRepository : TestCurrencyRepository
    private var workManagerSyncManager = TestWorkManagerSyncManager()

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Mock
    lateinit var sharedPreferences: SharedPreferences

    @OptIn(ExperimentalCoroutinesApi::class)

    @Before
    fun setUp(){
        currencyRepository = TestCurrencyRepository()

        MockitoAnnotations.openMocks(this)

        Dispatchers.setMain(testDispatcher)

        viewModel = CurrencyViewModel(
            currencyRepository = currencyRepository,
            sharedPreferences = sharedPreferences,
            workManagerSyncManager = workManagerSyncManager
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown(){ Dispatchers.resetMain() }

    @Test
    fun `initial state is Loading`(){
        assert(viewModel.uiState.value.isLoading)
    }

    @Test
    fun `state is not Loading when sync is done`() = testScope.runTest {
        workManagerSyncManager.emit(false)

        val job = launch {
            viewModel.uiState.take(1).toList()
        }

        job.join()

        assert(!viewModel.uiState.value.isLoading)
    }

    @Test
    fun `state has changed after a successful emission from the repository`() = testScope.runTest {
        workManagerSyncManager.emit(true)

        currencyRepository.sendExchangeRates(
            ExchangeRates(
                baseCurrency = "",
                lastUpdatedDate = "18-11-2026",
                rates = mapOf("USD" to 10.0, "EUR" to 0.92)
            )
        )

        workManagerSyncManager.emit(false)

        val job = launch {
            viewModel.uiState.take(1).toList()
        }

        job.join()

        assert(!viewModel.uiState.value.isLoading)
        assert(viewModel.uiState.value.indicativeExchangeRate.isNotEmpty())
    }
}