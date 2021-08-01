/*
 * Copyright 2019-2021 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.initprovider

import android.content.ContentProvider
import android.content.ContentValues
import android.net.Uri
import splitties.exceptions.unsupported

/**
 * THIS WILL BE REMOVED before Splitties 3.0.0 release.
 *
 * Use [AndroidX App Startup](https://developer.android.com/topic/libraries/app-startup) instead.
 *
 * Base class that was designed for [ContentProvider]s used for initialization purposes.
 */
@Deprecated(
    message = "Each ContentProvider adds a small app startup penalty, " +
        "which can add-up to significant delays for cold start. " +
        "Please, use AndroidX App Startup instead to avoid that problem. " +
        "This will be removed before Splitties 3.0.0 release.",
    level = DeprecationLevel.ERROR
)
abstract class InitProvider : ContentProvider() {
    final override fun insert(uri: Uri, values: ContentValues?) = unsupported()
    final override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ) = unsupported()

    final override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ) = unsupported()

    final override fun delete(
        uri: Uri,
        selection: String?,
        selectionArgs: Array<out String>?
    ) = unsupported()

    final override fun getType(uri: Uri) = unsupported()
}
