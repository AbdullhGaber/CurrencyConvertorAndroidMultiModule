package com.iti.currency_convertor

import android.content.SharedPreferences
import model.currency_convertor.CurrencyInfo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.core.data.repository.CurrencyRepository
import com.iti.core.data.worker.SyncManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.flow.update
import model.currency_convertor.ExchangeRates
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject
import androidx.core.content.edit


@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository,
    private val sharedPreferences: SharedPreferences,
    workManagerSyncManager: SyncManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(CurrencyConvertorUiState())
    val uiState = _uiState.asStateFlow()

    private lateinit var exchangeRates : ExchangeRates

    companion object {
        const val FROM_CURRENCY_KEY = "fromCurrency"
        const val FROM_CURRENCY_AMOUNT_KEY = "fromCurrencyAmount"
        const val TO_CURRENCY_KEY = "toCurrency"
    }
    init {
        workManagerSyncManager.isSyncing
            .onEach { isLoading ->
                _uiState.update {
                    it.copy(isLoading = isLoading)
                }
            }.launchIn(viewModelScope)
        initUiState()
    }
    private fun initUiState(){
        currencyRepository
            .getExchangeRates()
            .retryWhen { cause, _ -> cause is IllegalStateException  }
            .onEach { exchangeRates ->
                if(exchangeRates.rates.isEmpty()) return@onEach
                this.exchangeRates = exchangeRates
                _uiState.update { uiStateModel ->
                    uiStateModel.copy(
                        isLoading = false,
                        allCurrencies = exchangeRates.rates.keys.map {
                            CurrencyUiModel(code = it, value = "")
                        },
                        lastUpdated = fromDate(exchangeRates.lastUpdatedDate),
                    )
                }
                setInitialCurrencies()
            }.launchIn(viewModelScope)
    }

    private fun getUserCurrencies(): Pair<CurrencyInfo, CurrencyInfo> {
        val fromCurrencyCode = sharedPreferences.getString(FROM_CURRENCY_KEY, null)
        val toCurrencyCode = sharedPreferences.getString(TO_CURRENCY_KEY, null)


        var fromCurrency = CurrencyInfo(code = "USD", exchangeRates.rates.getValue(key = "USD"))
        var toCurrency = CurrencyInfo(code = "EUR", exchangeRates.rates.getValue(key = "EUR"))

        if (fromCurrencyCode != null) {
            fromCurrency = CurrencyInfo(fromCurrencyCode, exchangeRates.rates.getValue(fromCurrencyCode))
        }

        if (toCurrencyCode != null) {
            toCurrency = CurrencyInfo(toCurrencyCode, exchangeRates.rates.getValue(toCurrencyCode))
        }

        return Pair(fromCurrency, toCurrency)
    }
    private fun setInitialCurrencies() {
        val initialCurrencies = getUserCurrencies()
        val (fromCurrency, toCurrency) = initialCurrencies

        val convertedResults = convert(
            fromVsBaseValue = fromCurrency.value,
            toVsBaseValue = toCurrency.value,
            amount = sharedPreferences.getFloat(FROM_CURRENCY_AMOUNT_KEY, 1.0f).toDouble(),
        )

        val displayFinalToCurrencyValue = (convertedResults.toDouble() / sharedPreferences.getFloat(FROM_CURRENCY_AMOUNT_KEY, 1.0f).toDouble()).let {
            String.format(Locale.ENGLISH, "%.2f", it)
        }

        _uiState.update {
            it.copy(
                fromCurrency = CurrencyUiModel(fromCurrency.code, sharedPreferences.getFloat(FROM_CURRENCY_AMOUNT_KEY, 1.0f).toString()),
                toCurrency = CurrencyUiModel(toCurrency.code, convertedResults),
                indicativeExchangeRate = "1 ${fromCurrency.code} = $displayFinalToCurrencyValue ${toCurrency.code}"
            )
        }
    }

    private fun convert(fromVsBaseValue: Double, toVsBaseValue: Double, amount: Double) : String{
        return CurrencyConvertor.convert(
            fromCurrencyRateVsBaseCurrencyRate = fromVsBaseValue,
            toCurrencyRateVsBaseCurrencyRate = toVsBaseValue,
            amount = amount
        ).let {
            String.format(Locale.ENGLISH, "%.2f", it)
        }
    }

    fun onFromCurrencyChange(fromCurrency: CurrencyUiModel) {
        when(val result = fromCurrency.value.safeToDouble()){
            is StringToDoubleConversionResult.Valid -> {
               with(_uiState.value){
                   val convertedResults = convert(
                       fromVsBaseValue = exchangeRates.rates.getValue(fromCurrency.code),
                       toVsBaseValue = exchangeRates.rates.getValue(toCurrency.code),
                       amount = result.value
                   )

                   _uiState.update {
                       it.copy(
                           fromCurrency = fromCurrency,
                           toCurrency = it.toCurrency.copy(value = convertedResults),
                           indicativeExchangeRate = "1 ${fromCurrency.code} = ${
                               getIndicativeExchangeRate(
                                   fromCurrency.code,
                                   toCurrency.code,
                               )
                           } ${toCurrency.code}"

                       )
                   }

                   sharedPreferences.edit {
                       putString(FROM_CURRENCY_KEY, fromCurrency.code)
                   }

                   sharedPreferences.edit {
                       putFloat(FROM_CURRENCY_AMOUNT_KEY, fromCurrency.value.toFloat())
                   }
                   print(
                       sharedPreferences.getFloat(FROM_CURRENCY_AMOUNT_KEY, fromCurrency.value.toFloat() )
                   )
               }
            }

            is StringToDoubleConversionResult.Empty -> {
                _uiState.update {
                    it.copy(
                        fromCurrency = it.fromCurrency.copy(value = ""),
                        toCurrency = it.toCurrency.copy(value = "")
                    )
                }
            }

            StringToDoubleConversionResult.Invalid -> Unit
        }
    }

    fun onToCurrencyChange(toCurrency: CurrencyUiModel) {
        if (toCurrency.code != uiState.value.toCurrency.code) {
            _uiState.update {
                it.copy(
                    toCurrency = toCurrency
                )
            }
            onFromCurrencyChange(
                fromCurrency = uiState.value.fromCurrency
            )

            sharedPreferences.edit {
                putString(TO_CURRENCY_KEY, toCurrency.code)
            }
            return
        }

        when(val result = toCurrency.value.safeToDouble()){
            is StringToDoubleConversionResult.Valid -> {
                with(_uiState.value){
                    val convertedResults = convert(
                        fromVsBaseValue = exchangeRates.rates.getValue(toCurrency.code),
                        toVsBaseValue = exchangeRates.rates.getValue(fromCurrency.code),
                        amount = result.value
                    )

                    _uiState.update {
                        it.copy(
                            fromCurrency = it.fromCurrency.copy(value = convertedResults),
                            toCurrency = toCurrency,
                        )
                    }

                    sharedPreferences.edit {
                        putString(TO_CURRENCY_KEY, toCurrency.code)
                    }
                }
            }

            is StringToDoubleConversionResult.Empty -> {
                _uiState.update {
                    it.copy(
                        fromCurrency = it.fromCurrency.copy(value = ""),
                        toCurrency = it.toCurrency.copy(value = "")
                    )
                }
            }

            StringToDoubleConversionResult.Invalid -> Unit
        }
    }

    fun swapCurrencies() {
        _uiState.update {
            it.copy(
                fromCurrency = it.toCurrency,
                toCurrency = it.fromCurrency
            )
        }
    }

    private fun fromDate(date: String): String {
        return try {
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            OffsetDateTime.parse(date).format(formatter)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    private fun getIndicativeExchangeRate(
        fromCurrencyCode: String,
        toCurrencyCode: String
    ): String {
        return convert(
            fromVsBaseValue = exchangeRates.rates.getValue(fromCurrencyCode),
            toVsBaseValue = exchangeRates.rates.getValue(toCurrencyCode),
            amount = 1.0
        )
    }

    private fun String.safeToDouble(): StringToDoubleConversionResult {
        return if (this.endsWith("." )) {
            StringToDoubleConversionResult.Valid(dropLast(1).toDouble())
        } else if (isEmpty()) {
            StringToDoubleConversionResult.Empty
        } else {
            this.toDoubleOrNull()?.let {
                StringToDoubleConversionResult.Valid(it)
            } ?: StringToDoubleConversionResult.Invalid
        }
    }

    sealed interface StringToDoubleConversionResult{
        data class Valid(val value: Double) : StringToDoubleConversionResult
        object Empty : StringToDoubleConversionResult
        object Invalid : StringToDoubleConversionResult
    }
}