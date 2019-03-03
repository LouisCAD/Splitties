/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package com.louiscad.splittiessample.preview.permissions

class PermissionDeniedException(
    val permissionName: String,
    val doNotAskAgain: Boolean
) : SecurityException("Permission denied: $permissionName")
