/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.main

interface MainUi {
    suspend fun awaitFabClick()
    suspend fun awaitLaunchMaterialListDemoRequest()
    suspend fun awaitLaunchPermissionDemoRequest()
    suspend fun awaitLaunchSayHelloDemoRequest()
    suspend fun awaitToggleNightModeRequest()
    suspend fun awaitTrySoundRequest()
}
