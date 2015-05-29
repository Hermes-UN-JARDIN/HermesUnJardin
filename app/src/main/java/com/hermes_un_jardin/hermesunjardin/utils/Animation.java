package com.hermes_un_jardin.hermesunjardin.utils;

import android.animation.ValueAnimator;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by songdeming on 2015/5/29.
 */
public class Animation {

    /**
     * @param textView
     * @param text
     * @param color      alpha bit will be ignored.
     * @param startDelay
     * @param duration
     */
    public static void foo(final TextView textView, final String text, int color, long startDelay, long duration) {

        //
        SpannableString spannableString = new SpannableString(text);
        final List<MutableForegroundColorSpan> spans = new ArrayList<>();
        for (int i = 0; i < text.length(); i++) {
            MutableForegroundColorSpan span = new MutableForegroundColorSpan();
            span.setColor(color);
            spannableString.setSpan(span, i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spans.add(span);
        }

        //
        Collections.shuffle(spans);

        //
        textView.setText(spannableString);

        //
        ValueAnimator valueAnimator = new ValueAnimator().ofFloat(0f, 1f);
        valueAnimator.setStartDelay(startDelay);
        valueAnimator.setDuration(duration);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                long totalAlpha = (long) (255 * text.length() * value);

                for (MutableForegroundColorSpan span : spans) {
                    int color = span.getColor();

                    // Change 'alpha' value to control appearence.
                    if (totalAlpha > 255) {
                        color |= 0xff000000;

                        totalAlpha -= 255;
                    } else {
                        color &= 0x00ffffff;
                        color |= totalAlpha << 24;

                        totalAlpha = 0;
                    }

                    span.setColor(color);
                }

                textView.invalidate();
            }
        });
        valueAnimator.start();

    }

    public static void foo(final Object view, final Method setMethod, final String text, int color, long startDelay, long duration) {

        //
        final SpannableString spannableString = new SpannableString(text);
        final List<MutableForegroundColorSpan> spans = new ArrayList<>();
        for (int i = 0; i < text.length(); i++) {
            MutableForegroundColorSpan span = new MutableForegroundColorSpan();
            span.setColor(color);
            spannableString.setSpan(span, i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spans.add(span);
        }

        //
        Collections.shuffle(spans);

        //
        try {
            setMethod.invoke(view, spannableString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //
        ValueAnimator valueAnimator = new ValueAnimator().ofFloat(0f, 1f);
        valueAnimator.setStartDelay(startDelay);
        valueAnimator.setDuration(duration);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                long totalAlpha = (long) (255 * text.length() * value);

                for (MutableForegroundColorSpan span : spans) {
                    int color = span.getColor();

                    // Change 'alpha' value to control appearence.
                    if (totalAlpha > 255) {
                        color |= 0xff000000;

                        totalAlpha -= 255;
                    } else {
                        color &= 0x00ffffff;
                        color |= totalAlpha << 24;

                        totalAlpha = 0;
                    }

                    span.setColor(color);
                }

                try {
                    setMethod.invoke(view, spannableString);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        valueAnimator.start();

    }
}
