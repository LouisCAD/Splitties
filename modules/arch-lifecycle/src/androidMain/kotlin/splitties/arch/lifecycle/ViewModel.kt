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
    override fun <T : ViewModel?> create(modelClass: Class<T>) = factory() as T
}


//region Deprecated symbols because they made it into AndroidX (with a different name).

private const val deprecationMessageStart = "An equivalent to this extension is now provided in AndroidX"

@Deprecated(
    message = "$deprecationMessageStart Activity KTX",
    level = DeprecationLevel.ERROR,
    replaceWith = ReplaceWith(
        "viewModels<VM>()",
        "androidx.activity.app.viewModels"
    )
)
inline fun <reified VM : ViewModel> FragmentActivity.activityScope(): Lazy<VM> = viewModels()

@Deprecated(
    message = "$deprecationMessageStart Fragment KTX",
    level = DeprecationLevel.ERROR,
    replaceWith = ReplaceWith(
        "activityViewModels<VM>()",
        "androidx.fragment.app.activityViewModels"
    )
)
inline fun <reified VM : ViewModel> Fragment.activityScope(): Lazy<VM> = activityViewModels()

@Deprecated(
    message = "$deprecationMessageStart Fragment KTX",
    level = DeprecationLevel.ERROR,
    replaceWith = ReplaceWith(
        "viewModels<VM>()",
        "androidx.fragment.app.viewModels"
    )
)
inline fun <reified VM : ViewModel> Fragment.fragmentScope(): Lazy<VM> = viewModels()

@Deprecated(
    message = "$deprecationMessageStart Activity KTX",
    level = DeprecationLevel.ERROR,
    replaceWith = ReplaceWith(
        "viewModels<VM>(factoryProducer = { factory })",
        "androidx.activity.app.viewModels"
    )
)
inline fun <reified VM : ViewModel> FragmentActivity.activityScope(
    factory: ViewModelProvider.Factory
): Lazy<VM> = viewModels(factoryProducer = { factory })

@Deprecated(
    message = "$deprecationMessageStart Fragment KTX",
    level = DeprecationLevel.ERROR,
    replaceWith = ReplaceWith(
        "activityViewModels<VM>(factoryProducer = { factory })",
        "androidx.fragment.app.activityViewModels"
    )
)
inline fun <reified VM : ViewModel> Fragment.activityScope(
    factory: ViewModelProvider.Factory
): Lazy<VM> = activityViewModels(factoryProducer = { factory })

@Deprecated(
    message = "$deprecationMessageStart Fragment KTX",
    level = DeprecationLevel.ERROR,
    replaceWith = ReplaceWith(
        "viewModels<VM>(factoryProducer = { factory })",
        "androidx.fragment.app.viewModels"
    )
)
inline fun <reified VM : ViewModel> Fragment.fragmentScope(
    factory: ViewModelProvider.Factory
): Lazy<VM> = viewModels(factoryProducer = { factory })

//endregion

//region Deprecated symbols because we renamed to follow AndroidX naming.

private const val renamedDeprecationMessage = "This function is being renamed to follow AndroidX naming."

@Deprecated(
    message = renamedDeprecationMessage,
    level = DeprecationLevel.ERROR,
    replaceWith = ReplaceWith(
        "viewModels<VM>(factory)",
        "splitties.arch.lifecycle.viewModels"
    )
)
inline fun <reified VM : ViewModel> FragmentActivity.activityScope(
    noinline factory: () -> VM
): Lazy<VM> = viewModels(factory)

@Deprecated(
    message = renamedDeprecationMessage,
    level = DeprecationLevel.ERROR,
    replaceWith = ReplaceWith(
        "viewModels<VM>(factory)",
        "splitties.arch.lifecycle.activityViewModels"
    )
)
inline fun <reified VM : ViewModel> Fragment.activityScope(
    noinline factory: () -> VM
): Lazy<VM> = activityViewModels(factory)

@Deprecated(
    message = renamedDeprecationMessage,
    level = DeprecationLevel.ERROR,
    replaceWith = ReplaceWith(
        "viewModels<VM>(factory)",
        "splitties.arch.lifecycle.viewModels"
    )
)
inline fun <reified VM : ViewModel> Fragment.fragmentScope(
    noinline factory: () -> VM
): Lazy<VM> = viewModels(factory)

//endregion
