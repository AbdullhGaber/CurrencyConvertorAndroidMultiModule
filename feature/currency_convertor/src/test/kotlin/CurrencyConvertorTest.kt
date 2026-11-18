import com.iti.currency_convertor.CurrencyConvertor
import org.junit.Test

class CurrencyConvertorTest {
    @Test
    fun `convert value in USD to EUR`() {
        val usdVsUsd = 1.0
        val eurVsUsd = 0.98
        val amount = 1.0

        val result = CurrencyConvertor.convert(
            usdVsUsd,
            eurVsUsd,
            amount
        )

        assert(result == 0.98)

    }

    @Test
    fun `convert value USD to USD`() {
        val usdVsUsdPrice = 1.0
        val result = CurrencyConvertor.convert(
            usdVsUsdPrice,
            usdVsUsdPrice,
            20.5
        )
        assert(result == 20.5)
    }

    @Test
    fun `convert zero amount`() {
        val result = CurrencyConvertor.convert(
            1.0,
            1.0,
            0.0
        )

        assert(result == 0.0)
    }
}