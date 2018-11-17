/*
 * Copyright (c) 2018. Louis Cognault Ayeva Derman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package splitties.arch.lifecycle

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import splitties.checkedlazy.mainThreadLazy

inline fun <reified VM : ViewModel> FragmentActivity.activityScope() = mainThreadLazy {
    ViewModelProviders.of(this).get(VM::class.java)
}

inline fun <reified VM : ViewModel> Fragment.activityScope() = mainThreadLazy {
    ViewModelProviders.of(activity!!).get(VM::class.java)
}

inline fun <reified VM : ViewModel> Fragment.fragmentScope() = mainThreadLazy {
    ViewModelProviders.of(this).get(VM::class.java)
}

inline fun <reified VM : ViewModel> FragmentActivity.activityScope(
    factory: ViewModelProvider.Factory
) = mainThreadLazy {
    ViewModelProviders.of(this, factory).get(VM::class.java)
}

inline fun <reified VM : ViewModel> Fragment.activityScope(
    factory: ViewModelProvider.Factory
) = mainThreadLazy {
    ViewModelProviders.of(activity!!, factory).get(VM::class.java)
}

inline fun <reified VM : ViewModel> Fragment.fragmentScope(
    factory: ViewModelProvider.Factory
) = mainThreadLazy {
    ViewModelProviders.of(this, factory).get(VM::class.java)
}

inline fun <reified VM : ViewModel> FragmentActivity.activityScope(
    noinline factory: () -> VM
) = mainThreadLazy {
    ViewModelProviders.of(this, TypeSafeViewModelFactory(factory)).get(VM::class.java)
}

inline fun <reified VM : ViewModel> Fragment.activityScope(
    noinline factory: () -> VM
) = mainThreadLazy {
    ViewModelProviders.of(activity!!, TypeSafeViewModelFactory(factory)).get(VM::class.java)
}

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
