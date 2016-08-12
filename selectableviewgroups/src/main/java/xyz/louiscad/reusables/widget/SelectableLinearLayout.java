package xyz.louiscad.reusables.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import xyz.louiscad.reusables.R;

import static xyz.louiscad.reusables.widget.SelectableViewGroupsUtils.getDefaultForegroundSelector;

/**
 * LinearLayout with ripple effect / select foreground when touched
 */
@SuppressWarnings("unused")
public abstract class SelectableLinearLayout extends LinearLayout {

    private Drawable mForegroundSelector;

    public SelectableLinearLayout(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public SelectableLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public SelectableLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SelectableLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    @CallSuper
    @SuppressWarnings("WeakerAccess")
    protected void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        final TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.SelectableLinearLayout,
                defStyleAttr,
                defStyleRes
        );
        mForegroundSelector = ta.getDrawable(R.styleable.SelectableLinearLayout_foreground);
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
    public void draw(Canvas canvas) {
        super.draw(canvas);
        mForegroundSelector.draw(canvas);
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
