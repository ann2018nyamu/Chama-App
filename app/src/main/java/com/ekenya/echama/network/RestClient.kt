package com.ekenya.echama.network

import com.ekenya.echama.inc.AppConstants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit


object RestClient {
    //lateinit var apiService:Webservice
    const val CONNECTION_TIMEOUT = 60000L
    init {
        setRetrofitClient()
    }

     val authInterceptor = Interceptor {chain->

        var request = chain.request()
        var headerStrValue = ""
        if(request.url.toString().contains("token")){
           //ToDo use constant on the basic token  AppConstants.auth_url_token
            headerStrValue = "Basic Y2hhbWFfY2xpZW50aWQ6WTJoaGJXRnpaV055WlhRPQ=="
        }

        request = request.newBuilder()
            .addHeader("Authorization",headerStrValue)

            .method(request.method, request.body)
            .build()


        val rBody = request.body
        rBody?.contentType()

        val reqBuffer = Buffer()
        rBody?.writeTo(reqBuffer)
        var reqCharset: Charset = rBody?.contentType()?.charset(Charset.forName("UTF-8"))!!
        val headers = request.headers

        var apiReqStr = " \n////////////////REQUEST START/////////////////////\n"
        for ((index, value) in headers.withIndex()) {
            apiReqStr += String.format("Header: %s  %s",value.first,value.second)
        }
        apiReqStr += String.format(" \n Request: %s \n Params: %s",request.url.toUrl().toString(),reqBuffer.readString(reqCharset))
       // Timber.v(apiReqStr)
        val response = chain.proceed(request)
        try {

            val responseBody = response.body

            val source = responseBody?.source()
            source?.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
            val buffer = source?.buffer
            apiReqStr += "\n Response: "
            apiReqStr += buffer?.clone()?.readString(Charset.forName("UTF-8"))
            apiReqStr += "\n\n////////////// RESPONSE END////////////////////\n"

        }catch (e:HttpException){
            apiReqStr += "\nException $e  ${response.request.url}"
            apiReqStr += "\n\n///////////////RESPONSE END////////////////////////"
        }
        Timber.v(apiReqStr)


         response
    }

    var spec: ConnectionSpec = ConnectionSpec.Builder(ConnectionSpec.RESTRICTED_TLS)
        .tlsVersions(TlsVersion.TLS_1_3, TlsVersion.TLS_1_2)
      //  .cipherSuites(
//            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
//            CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
 //           CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256,
//            CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA,
//            CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256
 //       )
        .build()


    //OkhttpClient for building http request url
    private val appClient = OkHttpClient().newBuilder()
        .addInterceptor(authInterceptor)
        .connectionSpecs(listOf(spec))
//        .certificatePinner( CertificatePinner.Builder()
//            .add("demo-api.ekenya.co.ke", "sha256/nMqjbDWenPPz3XZ/kOv7V8tKAyrlLMZDFR2SuK1ufDs=")
//            .add("demo-api.ekenya.co.ke", "sha256/5kJvNEMw0KjrCAu7eXY5HZdvyCS13BbA0VJG1RSP91w=")
//            .add("demo-api.ekenya.co.ke", "sha256/r/mIkG3eEpVdm+u/ko/cwxzOMo1bk4TyHIlByibiA5E=")
//            .build())
        .build()
    private val appClient1 = UnsafeOkHttpClient.getUnsafeOkHttpClient()
        .connectionSpecs(listOf(spec))
        .addInterceptor(authInterceptor)
        .build()

    fun normalLogging():OkHttpClient{

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client =  //UnsafeOkHttpClient.getUnsafeOkHttpClient()
            OkHttpClient.Builder()
            .connectionSpecs(listOf(spec))
            .addInterceptor(interceptor)
            .build()

        return client
    }



    fun retrofit() : Retrofit = Retrofit.Builder()
        .baseUrl(AppConstants.base_url)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
//      .client(appClient)
//        .client(appClient1)
      .client(normalLogging())
        .build()

    val apiService  = retrofit().create(Webservice::class.java)




    private fun setRetrofitClient() {



//         fun createMoshi() = Moshi.Builder()
//            .add(KotlinJsonAdapterFactory())
//            .build()
////TODO ADD UNSAFE HTTP CERT
//        var unsafeOkhttp = UnsafeOkHttpClient.getUnsafeOkHttpClient()
//        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        val gson = Gson()
//        val retrofit = Retrofit.Builder()
//             //.client(provideOkHttpClient())
//            .client(unsafeOkhttp.build())
//            .baseUrl("https://demo-api.ekenya.co.ke/")
//
////            .baseUrl("https://test-api.ekenya.co.ke/chamaa/")
//            //.addConverterFactory(MoshiConverterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .addCallAdapterFactory(CoroutineCallAdapterFactory())
//            .build()

        //val authtoken = Credentials.basic("username", "password")
       // apiService = retrofit.create(Webservice::class.java)

    }


//    val certificatePinner=
//        CertificatePinner.Builder()
//            .add("test-api.ekenya.co.ke", "sha256/nMqjbDWenPPz3XZ/kOv7V8tKAyrlLMZDFR2SuK1ufDs=")
//            .build()
  //  fun provideOkHttpClient(): OkHttpClient {
//        val client = OkHttpClient.Builder()
//            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
//            .readTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
//            .writeTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
//            .certificatePinner(CertificatePinner.Builder()
//                .add("test-api.ekenya.co.ke", "sha256/5kJvNEMw0KjrCAu7eXY5HZdvyCS13BbA0VJG1RSP91w=")
//                .build())
//
//
//        //if(BuildConfig.DEBUG) {
//            val logging = HttpLoggingInterceptor()
//        //val logging = LoggingInterceptors() //TODO Interceptors
//            logging.level = HttpLoggingInterceptor.Level.BODY
//            client.addInterceptor(logging)
//        /*client.certificatePinner( CertificatePinner.Builder()
//            .add("https://test-api.ekenya.co.ke", "sha256/5kJvNEMw0KjrCAu7eXY5HZdvyCS13BbA0VJG1RSP91w=")
//            .build())*/
//       // }
//
//        return client.build()
 //   }





}


/*
class RestClient {
     val webservice: Webservice by lazy {
        Retrofit.Builder()
            .baseUrl("")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(Webservice::class.java)
    }
*/

    //constructor()
