@file:Suppress("PackageDirectoryMismatch", "SpellCheckingInspection", "unused")

import org.gradle.api.Incubating

@Incubating
object Square {

    const val moshi = "com.squareup.moshi:moshi:_"

    val okHttp3 = OkHttp3
    val retrofit2 = Retrofit2

    object OkHttp3 {
        private const val artifactPrefix = "com.squareup.okhttp3"
        const val okHttp = "$artifactPrefix:okhttp:_"
        const val loggingInterceptor = "$artifactPrefix:logging-interceptor:_"
        const val mockWebServer = "$artifactPrefix:mockwebserver:_"
    }

    object Retrofit2 {
        private const val artifact = "com.squareup.retrofit2"

        const val retrofit = "$artifact:retrofit:_"
        const val mock = "$artifact:retrofit-mock:_"

        val converter = Converter
        val adapter = Adapter

        object Converter {
            private const val artifact = "${Retrofit2.artifact}:converter"

            const val scalars = "$artifact-scalars:_"

            const val moshi = "$artifact-moshi:_"
            const val gson = "$artifact-gson:_"
            const val jackson = "$artifact-jackson:_"

            const val simpleXml = "$artifact-simplexml:_"
        }

        object Adapter {
            const val retrofitJava8 = "$artifact:adapter-java8:_"
            const val retrofitRxJava1 = "$artifact:adapter-rxjava:_"
            const val retrofitRxJava2 = "$artifact:adapter-rxjava2:_"
        }
    }
}
