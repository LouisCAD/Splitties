/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.extensions.preferences.ui

import android.content.SharedPreferences
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.splitties.extensions.recyclerview.ListAdapter
import splitties.collections.forEachByIndex
import splitties.collections.forEachWithIndex
import splitties.mainthread.checkMainThread

abstract class AbstractPrefsRecyclerViewAdapter : ListAdapter<Item, RecyclerView.ViewHolder> {

    constructor(diffCallback: DiffUtil.ItemCallback<Item>?) : super(diffCallback)
    constructor(config: AsyncDifferConfig<Item>) : super(config)
    constructor(list: List<Item>) : super(list) {
        ensurePrefsChangesRegisteredForList(list)
    }

    override fun submitList(list: List<Item>?) {
        ensurePrefsChangesRegisteredForList(list ?: emptyList())
        super.submitList(list)
    }

    private fun ensurePrefsChangesRegisteredForList(items: List<Item>) {
        checkMainThread() // Ensure no race condition.
        // No need to unregister the listener as no strong reference to it is kept by Android.
        // The listener should be garbage collected with this adapter.
        val itemsAdded = items.sumOf { item ->
            item.dependentPrefs.count { prefDelegate -> prefs.add(prefDelegate.preferences.prefs) }
        }
        if (itemsAdded > 0) prefs.forEach { prefs ->
            prefs.registerOnSharedPreferenceChangeListener(prefsListener)
        }
    }

    // Strong reference needed to keep the listener registered (Android keeps only a WeakReference).
    private val prefsListener = SharedPreferences.OnSharedPreferenceChangeListener { prefs, key ->
        currentList.forEachWithIndex { i, item ->
            item.dependentPrefs.forEachByIndex { prefDelegate ->
                if (prefDelegate.doesBelongTo(prefs) && prefDelegate.key == key) {
                    notifyItemChanged(i)
                }
            }
        }
    }

    private val prefs = mutableSetOf<SharedPreferences>()

    abstract override fun getItemViewType(position: Int): Int
}
