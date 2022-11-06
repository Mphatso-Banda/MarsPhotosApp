package com.example.android.marsphotos.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

//the base url for the retrofit service
private const val BASE_URL =
    "https://android-kotlin-fun-mars-server.appspot.com"

//add the moshi object builder
//For Moshi's annotations to work properly with Kotlin, in the Moshi builder, add the
// KotlinJsonAdapterFactory, and then call build()
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//add a retrofit builder to build and create a Retrofit object
private val retrofit = Retrofit.Builder()

/**
 * Retrofit needs the base URI for the web service, and a converter factory to build a web services
 * API. The converter tells Retrofit what to do with the data it gets back from the web service.
 * In this case, you want Retrofit to fetch a JSON response from the web service, and return it as
 * a String. Retrofit has a ScalarsConverter that supports strings and other primitive types,
 * so you call addConverterFactory() on the builder with an instance of ScalarsConverterFactory.
 * */
//        now changed to use the moshi converter to pass Json response into useful kotlin objects
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

//define an interface MarsApiService, that defines how Retrofit talks to the web server using HTTP requests

interface MarsApiService {
//    Use the @GET annotation to tell Retrofit that this is GET request, and specify an endpoint,
//    for that web service method
    @GET("photos")
//    make this a suspend fun so that it can be called in a coroutine
//    it should return a list of MarsPhoto
    suspend fun getPhotos(): List<MarsPhoto>
}

/**
 *  object declarations are used to declare singleton objects. Singleton pattern ensures that one,
 *  and only one, instance of an object is created, has one global point of access to that object.
 *  Object declaration's initialization is thread-safe and done at first access.
 * */

//the public singleton object that can be accessed from the rest of the app.

object MarsApi {
//     Add a lazily initialized retrofit object property
//     You make this lazy initialization, to make sure it is initialized at its first usage
    val retrofitService : MarsApiService by lazy {
    //initialize the retrofitService variable with the MarsApiService interface
    retrofit.create(MarsApiService::class.java)
}

}

/**
 * The Retrofit setup is done! Each time your app calls MarsApi.retrofitService, the caller will
 * access the same singleton Retrofit object that implements MarsApiService which is created on the
 * first access
 * */
