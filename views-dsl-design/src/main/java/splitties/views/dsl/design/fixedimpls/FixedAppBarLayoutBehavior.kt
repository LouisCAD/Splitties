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
package splitties.views.dsl.design.fixedimpls

import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.view.View

/**
 * Workaround for [https://issuetracker.google.com/66996774](https://issuetracker.google.com/66996774).
 *
 * Credits to
 * [the file in this repo](https://github.com/DreaminginCodeZH/Douya/blob/70566834f268712fd44e762312b2b0fffada5e53/app/src/main/java/me/zhanghai/android/douya/ui/FixNestedScrollAppBarLayout.java)
 * inspired by [this gist](https://gist.github.com/chrisbanes/8391b5adb9ee42180893300850ed02f2).
 */
@PublishedApi
internal class FixedAppBarLayoutBehavior : AppBarLayout.Behavior() {

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: AppBarLayout,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) = super.onNestedScroll(
        coordinatorLayout,
        child,
        target,
        dxConsumed,
        dyConsumed,
        dxUnconsumed,
        dyUnconsumed,
        type
    ).also { stopNestedScrollIfNeeded(dyUnconsumed, child, target, type) }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: AppBarLayout,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) = super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type).also {
        stopNestedScrollIfNeeded(dy, child, target, type)
    }

    private fun stopNestedScrollIfNeeded(dy: Int, child: AppBarLayout, target: View, type: Int) {
        if (type == ViewCompat.TYPE_NON_TOUCH) {
            val currOffset = topAndBottomOffset
            if (dy < 0 && currOffset == 0 || dy > 0 && currOffset == -child.totalScrollRange) {
                ViewCompat.stopNestedScroll(target, ViewCompat.TYPE_NON_TOUCH)
            }
        }
    }
}
