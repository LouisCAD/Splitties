package com.louiscad.splitties



/**
 * The actual dependency version comes from `gradle.properties`
 * from either `version.$NAME=xxx` or `version.com.squareup.okhttp=xxx` or `version.com.squreup.retrofit2=xxxx`
 **/
object Square {
    private const val okHttpVersion = "+"
    private const val retrofitversion = "+"

    const val okHttp = "com.squareup.okhttp3:okhttp:$okHttpVersion"
    const val okHttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$okHttpVersion"
    const val okhttpMockWebserver = "com.squareup.okhttp3:mockwebserver:$okHttpVersion"

    private const val artifact = "com.squareup.retrofit2"
    const val retrofit = "$artifact:retrofit:$retrofitversion"
    const val retrofitMoshi = "$artifact:converter-moshi:$retrofitversion"
    const val retrofitGson = "$artifact:converter-moshi:$retrofitversion"
    const val retrofitJava8 = "$artifact:adapter-java8:$retrofitversion"
    const val retrofitMock = "$artifact:retrofit-mock:$retrofitversion"
    const val retrofitSimplexml = "$artifact:converter-simplexml:$retrofitversion"
    const val retrofitScalars = "$artifact:converter-scalars:$retrofitversion"
    const val retrofitJackson = "$artifact:converter-jackson:$retrofitversion"
    const val retrofitRxjava1 = "$artifact:adapter-rxjava:$retrofitversion"
    const val retrofitRxjava2 = "$artifact:adapter-rxjava2:$retrofitversion"



    private const val moshiVersion = "+"
    const val moshi = "com.squareup.moshi:moshi:$moshiVersion"
}
