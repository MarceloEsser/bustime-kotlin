package marcelo.esser.com.bustimek.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 * @since 19/02/19
 */
class NetworkHandler<T> {
    private var SOGAL_URL: String = "http://sogal.com.br/"
    private lateinit var instance: NetworkHandler<T>
    private lateinit var retrofit: Retrofit
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var tClass: Class<T>
    private val logginInterceptor: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    companion object {
        fun <T> build(): T {
            return build()
        }

        fun <T> getInstance(mclass: Class<T>): NetworkHandler<T> {
            return getInstance(mclass)
        }

        fun retrofitBuilder(): Retrofit {
            return retrofitBuilder()
        }
    }

    private fun getInstance(mclass: Class<T>): NetworkHandler<T> {
        instance.tClass = mclass

        return instance
    }

    internal fun build(): T {
        return retrofit.create(tClass)
    }

    private fun retrofitBuilder(): Retrofit {
        instance.retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(instance.SOGAL_URL)
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
