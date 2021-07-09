/*
 * Copyright 2019-2021 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package splitties.views.dsl.material.experimental

import android.content.Context
import android.view.View
import android.widget.*
import androidx.annotation.AttrRes
import com.google.android.material.R
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.appbar.ConfigChangesHandlingCollapsingToolbarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.radiobutton.MaterialRadioButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import splitties.views.dsl.core.experimental.*

/**
 * Matches [com.google.android.material.theme.MaterialComponentsViewInflater] content plus other MDC-only
 * views.
 */
internal inline fun <reified V : View> instantiateMaterialView(
    clazz: Class<out V>,
    context: Context
): V? = if (context.hasMaterialTheme().not()) null else when (clazz) {
    Button::class.java -> MaterialButton(context)
    CheckBox::class.java -> MaterialCheckBox(context)
    RadioButton::class.java -> MaterialRadioButton(context)
    TextView::class.java -> MaterialTextView(context)
    AutoCompleteTextView::class.java -> MaterialAutoCompleteTextView(context)
    // Material Components only views below
    FloatingActionButton::class.java -> FloatingActionButton(context)
    MaterialCardView::class.java -> MaterialCardView(context)
    AppBarLayout::class.java -> AppBarLayout(context)
    NavigationView::class.java -> NavigationView(context)
    BottomNavigationView::class.java -> BottomNavigationView(context)
    CollapsingToolbarLayout::class.java -> ConfigChangesHandlingCollapsingToolbarLayout(
        context
    )
    TabLayout::class.java -> TabLayout(context)
    TextInputLayout::class.java -> TextInputLayout(context)
    TextInputEditText::class.java -> TextInputEditText(context)
    ShapeableImageView::class.java -> ShapeableImageView(context)
    else -> null
} as V?

/**
 * Matches [com.google.android.material.theme.MaterialComponentsViewInflater] content plus other MDC-only
 * views.
 */
internal inline fun <reified V : View> instantiateThemeAttrStyledMaterialView(
    clazz: Class<out V>,
    context: Context,
    @AttrRes styleThemeAttribute: Int
): V? = if (context.hasMaterialTheme().not()) null else when (clazz) {
    Button::class.java -> MaterialButton(context, null, styleThemeAttribute)
    CheckBox::class.java -> MaterialCheckBox(context, null, styleThemeAttribute)
    RadioButton::class.java -> MaterialRadioButton(context, null, styleThemeAttribute)
    TextView::class.java -> MaterialTextView(context, null, styleThemeAttribute)
    AutoCompleteTextView::class.java -> MaterialAutoCompleteTextView(context, null, styleThemeAttribute)
    // Material Components only views below
    FloatingActionButton::class.java -> FloatingActionButton(context, null, styleThemeAttribute)
    MaterialCardView::class.java -> MaterialCardView(context, null, styleThemeAttribute)
    AppBarLayout::class.java -> AppBarLayout(context, null, styleThemeAttribute)
    NavigationView::class.java -> NavigationView(context, null, styleThemeAttribute)
    BottomNavigationView::class.java -> BottomNavigationView(context, null, styleThemeAttribute)
    CollapsingToolbarLayout::class.java -> ConfigChangesHandlingCollapsingToolbarLayout(
        context, null, styleThemeAttribute
    )
    TabLayout::class.java -> TabLayout(context, null, styleThemeAttribute)
    TextInputLayout::class.java -> TextInputLayout(context, null, styleThemeAttribute)
    TextInputEditText::class.java -> TextInputEditText(context, null, styleThemeAttribute)
    ShapeableImageView::class.java -> ShapeableImageView(context, null, styleThemeAttribute)
    else -> null
} as V?

private fun Context.hasMaterialTheme(): Boolean = hasThemeAttributes(MATERIAL_CHECK_ATTRS)

private val MATERIAL_CHECK_ATTRS = intArrayOf(R.attr.colorPrimaryVariant)
