package marcelo.esser.com.bustimek.service

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 * @since 19/02/19
 */
class NetworkHandler<T> {
    private val SOGAL_URL: String = "http://sogal.com.br/"
    private val VICASA_URL: String = "http://www.vicasa.com.br/"
    private val instance: NetworkHandler<T> by lazy {
        NetworkHandler<T>()
    }
    private lateinit var retrofit: Retrofit
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var tClass: Class<T>

    private val logginInterceptor: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    companion object {

        fun <T> getInstance(mclass: Class<T>): NetworkHandler<T> {
            return NetworkHandler<T>().getInstance(mclass)
        }
    }

    private fun getInstance(mclass: Class<T>): NetworkHandler<T> {
        instance.tClass = mclass

        return instance
    }

    internal fun build(): T {
        return retrofitBuilder().create(tClass)
    }

    private fun retrofitBuilder(): Retrofit {
        val gson: Gson = GsonBuilder()
            .disableHtmlEscaping()
            .setLenient().create()

        instance.retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(instance.VICASA_URL)
            .client(httpClient())
            .build()

        return instance.retrofit
    }

    private fun httpClient(): OkHttpClient {
        instance.okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logginInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        return instance.okHttpClient
    }
}
