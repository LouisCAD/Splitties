/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.arch.lifecycle

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import splitties.checkedlazy.mainThreadLazy

@ObsoleteSplittiesLifecycleApi
inline fun <reified VM : ViewModel> FragmentActivity.activityScope() = mainThreadLazy {
    ViewModelProviders.of(this).get(VM::class.java)
}

@ObsoleteSplittiesLifecycleApi
inline fun <reified VM : ViewModel> Fragment.activityScope() = mainThreadLazy {
    ViewModelProviders.of(activity!!).get(VM::class.java)
}

@ObsoleteSplittiesLifecycleApi
inline fun <reified VM : ViewModel> Fragment.fragmentScope() = mainThreadLazy {
    ViewModelProviders.of(this).get(VM::class.java)
}

@ObsoleteSplittiesLifecycleApi
inline fun <reified VM : ViewModel> FragmentActivity.activityScope(
    factory: ViewModelProvider.Factory
) = mainThreadLazy {
    ViewModelProviders.of(this, factory).get(VM::class.java)
}

@ObsoleteSplittiesLifecycleApi
inline fun <reified VM : ViewModel> Fragment.activityScope(
    factory: ViewModelProvider.Factory
) = mainThreadLazy {
    ViewModelProviders.of(activity!!, factory).get(VM::class.java)
}

@ObsoleteSplittiesLifecycleApi
inline fun <reified VM : ViewModel> Fragment.fragmentScope(
    factory: ViewModelProvider.Factory
) = mainThreadLazy {
    ViewModelProviders.of(this, factory).get(VM::class.java)
}

@ObsoleteSplittiesLifecycleApi
inline fun <reified VM : ViewModel> FragmentActivity.activityScope(
    noinline factory: () -> VM
) = mainThreadLazy {
    ViewModelProviders.of(this, TypeSafeViewModelFactory(factory)).get(VM::class.java)
}

@ObsoleteSplittiesLifecycleApi
inline fun <reified VM : ViewModel> Fragment.activityScope(
    noinline factory: () -> VM
) = mainThreadLazy {
    ViewModelProviders.of(activity!!, TypeSafeViewModelFactory(factory)).get(VM::class.java)
}

@ObsoleteSplittiesLifecycleApi
inline fun <reified VM : ViewModel> Fragment.fragmentScope(
    noinline factory: () -> VM
) = mainThreadLazy {
    ViewModelProviders.of(this, TypeSafeViewModelFactory(factory)).get(VM::class.java)
}

@PublishedApi
internal class TypeSafeViewModelFactory<VM : ViewModel>(
    private val factory: () -> VM
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) = factory() as T
}
