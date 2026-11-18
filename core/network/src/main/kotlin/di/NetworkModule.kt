package di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import interceptor.HeadersInterceptor
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import service.CurrencyService
import utils.Constants
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        return OkHttpClient
            .Builder()
            .addNetworkInterceptor(loggingInterceptor)
            .addNetworkInterceptor(HeadersInterceptor(mapOf("apiKey" to "cur_live_bN1PcVzWSogK7Bs5RzLWMtdktTIW8gQqbkLp0ieH")))
            .build()
    }

    @Singleton
    @Provides
    fun provideCurrencyRetrofitService(
        client: OkHttpClient
    ): CurrencyService {
        return Retrofit
            .Builder()
            .baseUrl(Constants.CurrencyConvertorBaseUrl)
            .client(client)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(CurrencyService::class.java)
    }

}