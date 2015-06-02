package com.hermes_un_jardin.hermesunjardin.utils;

import android.graphics.BlurMaskFilter;
import android.graphics.MaskFilter;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;

/**
 * Created by songdeming on 2015/6/1.
 */
public class BlurSpan extends CharacterStyle
        implements UpdateAppearance {

    private MaskFilter mMaskFilter;
    private float mRadius;

    public BlurSpan() {
        this(0.0);
    }

    public BlurSpan(double radius) {
        this.mRadius = (float) radius;
    }

    public float getRadius() {
        return mRadius;
    }

    public void setRadius(double radius) {
        this.mRadius = (float) radius;
        mMaskFilter = new BlurMaskFilter((float) radius, BlurMaskFilter.Blur.NORMAL);
    }

    @Override
    public void updateDrawState(TextPaint tp) {
        tp.setMaskFilter(mMaskFilter);
    }
}
