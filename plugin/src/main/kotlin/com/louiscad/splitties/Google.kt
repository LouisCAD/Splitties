package com.louiscad.splitties

/**
 * The actual dependency version comes from `gradle.properties`
 * from either `version.$NAME=xxx` or `version.com.google.android.$GROUP=xxx`
 **/
object Google {
    const val material = "com.google.android.material:material:1.0.0"
    private const val wearOsVersion = "2.4.0"
    const val wearable = "com.google.android.wearable:wearable:$wearOsVersion"
    const val supportWearable = "com.google.android.support:wearable:$wearOsVersion"



    /**
     * The actual dependency version comes from `gradle.properties`
     * from either `version.$NAME=xxx` or `version.com.google.android.gms=xxx`
     **/
    val playServices = PlayServices
    object PlayServices {
        private const val artifactPrefix = "com.google.android.gms:play-services"
        const val base = "$artifactPrefix-base:16.0.1"
        const val auth = "$artifactPrefix-auth:16.0.1"
        const val location = "$artifactPrefix-location:16.0.0"
        const val tasks = "$artifactPrefix-tasks:16.0.1"
        private const val visionVersion = "17.0.2"
        const val vision = "$artifactPrefix-vision:$visionVersion"
        const val visionCommon = "$artifactPrefix-vision-common:$visionVersion"
        const val wearable = "$artifactPrefix-wearable:16.0.1"
    }
}
