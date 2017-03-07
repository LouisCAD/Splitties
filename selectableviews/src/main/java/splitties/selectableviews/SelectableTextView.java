/*
 * Copyright (c) 2017. Louis Cognault Ayeva Derman
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

package splitties.selectableviews;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import xyz.louiscad.selectableviewgroups.R;

import static splitties.selectableviews.SelectableViewsUtils.getDefaultForegroundSelector;

public class SelectableTextView extends AppCompatTextView {

    private Drawable mForegroundSelector;

    public SelectableTextView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public SelectableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public SelectableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @CallSuper
    @SuppressWarnings("WeakerAccess")
    protected void init(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        final TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.SelectableTextView,
                defStyleAttr,
                defStyleRes
        );
        mForegroundSelector = ta.getDrawable(R.styleable.SelectableTextView_foreground);
        ta.recycle();
        if (mForegroundSelector == null) {
            mForegroundSelector = getDefaultForegroundSelector(context);
        }
        mForegroundSelector.setCallback(this);
        setWillNotDraw(false);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        mForegroundSelector.setState(getDrawableState());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mForegroundSelector.setBounds(0, 0, w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mForegroundSelector.draw(canvas);
    }

    @Override
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        mForegroundSelector.jumpToCurrentState();
    }

    @Override
    protected boolean verifyDrawable(@NonNull Drawable who) {
        return super.verifyDrawable(who) || (who == mForegroundSelector);
    }

    @Override
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void dispatchDrawableHotspotChanged(float x, float y) {
        super.dispatchDrawableHotspotChanged(x, y);
        mForegroundSelector.setHotspot(x, y);
    }
}
