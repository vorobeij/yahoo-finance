package vorobeij.yfinance

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import vorobeij.yfinance.cache.NetworkCache
import vorobeij.yfinance.cache.NetworkFileCache

private const val BASE_URL = "https://query2.finance.yahoo.com/v10/finance/"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .client(
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            })
            .build()
    )
    .build()

private val api = retrofit.create<YahooApi>()

class YahooFinance(
    cache: NetworkCache = NetworkFileCache()
) : YahooFinanceApi by YahooRepository(api, cache)
