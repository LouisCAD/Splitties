package com.louiscad.splitties

/**
 * The actual dependency version comes from `gradle.properties`
 * from either `version.$NAME=xxx` or `version.com.squareup.okhttp=xxx` or `version.com.squreup.retrofit2=xxxx`
 **/
object Square {
    private const val okHttpVersion = "3.12.0"
    const val okHttp = "com.squareup.okhttp3:okhttp:$okHttpVersion"
    const val okHttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$okHttpVersion"
    private const val retrofitversion = "2.5.0"
    const val retrofit2 = "com.squareup.retrofit2:retrofit:$retrofitversion"
    const val retrofit2ConverterMoshi = "com.squareup.retrofit2:converter-moshi:$retrofitversion"
    private const val moshiVersion = "1.5.0"
    const val moshi = "com.squareup.moshi:moshi:$moshiVersion"
}
