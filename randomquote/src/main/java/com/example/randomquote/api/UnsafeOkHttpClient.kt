package com.example.randomquote.api

import okhttp3.OkHttpClient
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.*

//"SSL Error: Certificate has expired"
// Disable SSL Verification

/**
 * What this does:
 *
 * Configures the HTTPS client to be completely insecure.
 *
 * This setup:
 * - Trusts all SSL/TLS certificates, regardless of validity.
 * - Ignores certificate expiration dates.
 * - Ignores hostname mismatches between the certificate and the server.
 * - Forces HTTPS connections to behave like plain HTTP, disabling standard security checks.
 *
 * ⚠️ **Warning:** This should **never** be used in production, as it completely bypasses HTTPS security and leaves the app vulnerable to attacks.
 */

object UnsafeOkHttpClient {

    fun getUnsafeOkHttpClient(): OkHttpClient {
        val trustAllCerts = arrayOf<X509TrustManager>(
            object : X509TrustManager {
                override fun checkClientTrusted(
                    chain: Array<X509Certificate>,
                    authType: String
                ) {}

                override fun checkServerTrusted(
                    chain: Array<X509Certificate>,
                    authType: String
                ) {}

                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            }
        )

        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, trustAllCerts, SecureRandom())

        return OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory, trustAllCerts[0])
            .hostnameVerifier(HostnameVerifier { _, _ -> true })
            .build()
    }
}