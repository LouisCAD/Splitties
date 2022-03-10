/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.preferences.ui

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.splitties.R
import com.example.splitties.extensions.preferences.ui.BoolPrefItem
import com.example.splitties.extensions.preferences.ui.BuiltInItem
import com.example.splitties.extensions.preferences.ui.PrefsRecyclerViewAdapter
import com.example.splitties.extensions.preferences.ui.StaticHeader
import com.example.splitties.preferences.SamplePreferences
import kotlinx.coroutines.suspendCancellableCoroutine
import splitties.resources.drawable
import splitties.views.dsl.core.Ui
import splitties.views.dsl.core.withTheme
import splitties.views.dsl.idepreview.UiPreView
import splitties.views.dsl.recyclerview.recyclerView
import splitties.views.recyclerview.verticalLayoutManager

class SamplePreferencesUi(override val ctx: Context) : Ui {

    private fun Context.createItems(prefs: SamplePreferences): List<BuiltInItem> = listOf(
        StaticHeader("Annoying stuff"),
        BoolPrefItem(
            delegate = prefs.enableAnnoyingFeaturesField,
            presentation = BoolPrefItem.Presentation.Switch.TwoText,
            firstLineText = { "Enable annoying features." },
            secondLineText = { "If enabled, the app may be annoying to use." },
            getIconDrawable = { enabled ->
                drawable(
                    if (enabled) R.drawable.ic_mood_bad_black_24dp else R.drawable.ic_mood_black_24dp
                )!!
            },
            isEnabled = { true }
        ),
        BoolPrefItem(
            delegate = prefs.showAnnoyingPopupAtLaunchField,
            presentation = BoolPrefItem.Presentation.CheckBox.TwoText,
            firstLineText = { "Popup at launch" },
            secondLineText = { "A disruptive popup at launch is a great technique to annoy the user" },
            getIconDrawable = { drawable(R.drawable.ic_report_black_24dp)!! },
            isEnabled = { prefs.enableAnnoyingFeaturesField.value },
            dependentPrefs = listOf(prefs.enableAnnoyingFeaturesField)
        ),
        BoolPrefItem(
            delegate = prefs.showAnnoyingPopupInLoopField,
            presentation = BoolPrefItem.Presentation.CheckBox.TwoText,
            firstLineText = { "Show annoying popup in loop" },
            secondLineText = { "Repeating a technique can increase its success" },
            getIconDrawable = { drawable(R.drawable.ic_repeat_black_24dp)!! },
            isEnabled = { prefs.enableAnnoyingFeaturesField.value },
            dependentPrefs = listOf(prefs.enableAnnoyingFeaturesField)
        )
    )

    suspend fun run(): Nothing {
        val prefs = SamplePreferences()
        val listAdapter = PrefsRecyclerViewAdapter(ctx.createItems(prefs))
        //TODO: Use payloads for update notifications and enable change animations again.
        (root.itemAnimator as DefaultItemAnimator).supportsChangeAnimations = false
        root.adapter = listAdapter
        suspendCancellableCoroutine<Nothing> {}
    }

    override val root = recyclerView {
        layoutManager = verticalLayoutManager()
    }
}

//region IDE preview
@Deprecated("For IDE preview only", level = DeprecationLevel.HIDDEN)
private class SamplePreferencesUiPreview(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : UiPreView(
    context = context.withTheme(R.style.AppTheme),
    attrs = attrs,
    defStyleAttr = defStyleAttr,
    createUi = { SamplePreferencesUi(it) }
)
//endregion
