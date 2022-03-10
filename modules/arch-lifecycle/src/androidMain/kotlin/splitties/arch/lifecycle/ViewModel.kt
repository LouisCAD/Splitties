/*
 * Copyright 2019-2021 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.arch.lifecycle

import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <reified VM : ViewModel> FragmentActivity.viewModels(
    noinline factory: () -> VM
): Lazy<VM> = viewModels { TypeSafeViewModelFactory(factory) }

inline fun <reified VM : ViewModel> Fragment.activityViewModels(
    noinline factory: () -> VM
): Lazy<VM> = activityViewModels { TypeSafeViewModelFactory(factory) }

inline fun <reified VM : ViewModel> Fragment.viewModels(
    noinline factory: () -> VM
): Lazy<VM> = viewModels { TypeSafeViewModelFactory(factory) }

@PublishedApi
internal class TypeSafeViewModelFactory<VM : ViewModel>(
    private val factory: () -> VM
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = factory() as T
}
