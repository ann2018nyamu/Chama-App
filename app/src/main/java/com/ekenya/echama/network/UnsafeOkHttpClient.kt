package com.ekenya.echama.network

import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.net.InetAddress
import java.net.Socket
import java.net.UnknownHostException
import java.security.cert.CertificateException
import java.util.concurrent.TimeUnit
import javax.net.ssl.*


class UnsafeOkHttpClient {
    companion object {
        fun getUnsafeOkHttpClient(): OkHttpClient.Builder {
            try {
                // Create a trust manager that does not validate certificate chains
                val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                    @Throws(CertificateException::class)
                          override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                    }

                    override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                        return arrayOf()
                    }
                })

                // Install the all-trusting trust manager
                val sslContext = SSLContext.getInstance("TLS")
                sslContext.init(null, trustAllCerts, java.security.SecureRandom())
                // Create an ssl socket factory with our all-trusting manager
                val sslSocketFactory = sslContext.socketFactory

                val builder = OkHttpClient.Builder()
                builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                builder.hostnameVerifier(HostnameVerifier { hostname: String?, session: SSLSession? -> true })
                    .connectTimeout(RestClient.CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                    .readTimeout(RestClient.CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                    .writeTimeout(RestClient.CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
//                    .certificatePinner( CertificatePinner.Builder()
//                        .add("demo-api.ekenya.co.ke", "sha256/nMqjbDWenPPz3XZ/kOv7V8tKAyrlLMZDFR2SuK1ufDs=")
//                        .add("demo-api.ekenya.co.ke", "sha256/5kJvNEMw0KjrCAu7eXY5HZdvyCS13BbA0VJG1RSP91w=")
//                        .add("demo-api.ekenya.co.ke", "sha256/r/mIkG3eEpVdm+u/ko/cwxzOMo1bk4TyHIlByibiA5E=")
//                        .build())


                //if(BuildConfig.DEBUG) {
                val logging = HttpLoggingInterceptor()
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                //val logging = LoggingInterceptors() //TODO Interceptors
                logging.level = HttpLoggingInterceptor.Level.BODY

                builder.addInterceptor( interceptor)
//              builder.addInterceptor( logging)

                return builder
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
    }
}

class TLSSocketFactory : SSLSocketFactory() {
    private val delegate: SSLSocketFactory
    override fun getDefaultCipherSuites(): Array<String> {
        return delegate.defaultCipherSuites
    }

    override fun getSupportedCipherSuites(): Array<String> {
        return delegate.supportedCipherSuites
    }

    @Throws(IOException::class)
    override fun createSocket(): Socket? {
        return enableTLSOnSocket(delegate.createSocket())
    }

    @Throws(IOException::class)
    override fun createSocket(
        s: Socket?,
        host: String?,
        port: Int,
        autoClose: Boolean
    ): Socket? {
        return enableTLSOnSocket(delegate.createSocket(s, host, port, autoClose))
    }

    @Throws(IOException::class, UnknownHostException::class)
    override fun createSocket(host: String?, port: Int): Socket? {
        return enableTLSOnSocket(delegate.createSocket(host, port))
    }

    @Throws(IOException::class, UnknownHostException::class)
    override fun createSocket(
        host: String?,
        port: Int,
        localHost: InetAddress?,
        localPort: Int
    ): Socket? {
        return enableTLSOnSocket(delegate.createSocket(host, port, localHost, localPort))
    }

    @Throws(IOException::class)
    override fun createSocket(host: InetAddress?, port: Int): Socket? {
        return enableTLSOnSocket(delegate.createSocket(host, port))
    }

    @Throws(IOException::class)
    override fun createSocket(
        address: InetAddress?,
        port: Int,
        localAddress: InetAddress?,
        localPort: Int
    ): Socket? {
        return enableTLSOnSocket(delegate.createSocket(address, port, localAddress, localPort))
    }

    private fun enableTLSOnSocket(socket: Socket?): Socket? {
        if (socket != null && socket is SSLSocket) {
            (socket as SSLSocket).enabledProtocols = arrayOf("TLSv1.1", "TLSv1.2")
        }
        return socket
    }

    init {
        val context = SSLContext.getInstance("TLS")
        context.init(null, null, null)
        delegate = context.socketFactory
    }
}
