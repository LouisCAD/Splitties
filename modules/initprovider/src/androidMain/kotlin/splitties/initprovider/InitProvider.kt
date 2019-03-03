/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.initprovider

import android.content.ContentProvider
import android.content.ContentValues
import android.net.Uri
import splitties.exceptions.unsupported

/**
 * Base class for [ContentProvider]s used for initialization purposes.
 */
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
