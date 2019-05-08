/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.extensions.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import androidx.lifecycle.ViewModel as ArchViewModel

open class ViewModel : ArchViewModel(), CoroutineScope {
    private val job = SupervisorJob()
    final override val coroutineContext = job + Dispatchers.Main

    final override fun onCleared() {
        job.cancel()
    }
}
