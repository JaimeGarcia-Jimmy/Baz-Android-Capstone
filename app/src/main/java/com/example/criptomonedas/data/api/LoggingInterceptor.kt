package com.example.criptomonedas.data.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class LoggingInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        //Retrieve original request
        val originalRequest = chain.request()

        //Create modified request wwith custom header
        val modifiedRequest = originalRequest.newBuilder()
            .header("user-agent", "esta peticion viene de la app de Criptomonedas")
            .method(originalRequest.method(), originalRequest.body())
            .build()

        //Log modified request
        val t1 = System.nanoTime()
        Log.i("LoggingInterceptor", "Sending request ${modifiedRequest.url()} on \n" +
                "${chain.connection()} ${modifiedRequest.headers()}")

        //Execute request
        val response = chain.proceed(modifiedRequest)

        //Log response
        val t2 = System.nanoTime()
        Log.i("LoggingInterceptor", "Received response for ${response.request().url()} " +
                "in ${(t2 - t1) / 1e6} \n ${response.headers()}")

        return response
    }
}