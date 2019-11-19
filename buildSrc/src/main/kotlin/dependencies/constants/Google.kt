@file:Suppress("PackageDirectoryMismatch", "SpellCheckingInspection", "unused")

import org.gradle.api.Incubating

@Incubating
object Google {
    const val material = "com.google.android.material:material:_"

    private const val wearOsVersion = "_"

    const val wearable = "com.google.android.wearable:wearable:$wearOsVersion"
    const val supportWearable = "com.google.android.support:wearable:$wearOsVersion"

    val playServices = PlayServices

    object PlayServices {
        private const val artifactPrefix = "com.google.android.gms:play-services"
        const val base = "$artifactPrefix-base:_"
        const val auth = "$artifactPrefix-auth:_"
        const val location = "$artifactPrefix-location:_"
        const val tasks = "$artifactPrefix-tasks:_"
        private const val visionVersion = "_"
        const val vision = "$artifactPrefix-vision:$visionVersion"
        const val visionCommon = "$artifactPrefix-vision-common:$visionVersion"
        const val wearable = "$artifactPrefix-wearable:_"
    }
}
