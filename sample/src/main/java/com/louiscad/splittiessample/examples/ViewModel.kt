package com.louiscad.splittiessample.examples

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
