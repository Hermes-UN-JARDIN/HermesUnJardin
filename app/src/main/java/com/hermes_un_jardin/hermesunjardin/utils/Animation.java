package com.hermes_un_jardin.hermesunjardin.utils;

import android.animation.ValueAnimator;
import android.app.ActionBar;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.animation.AccelerateInterpolator;
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
    public static void firework(final TextView view, final String text, int color, long startDelay, long duration) {
        Method setMethod = null;
        try {
            setMethod = view.getClass().getDeclaredMethod("setText", CharSequence.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        Animation.firework(view, setMethod, text, color, startDelay, duration);
    }

    public static void firework(final ActionBar view, final String text, int color, long startDelay, long duration) {
        Method setMethod = null;
        try {
            setMethod = view.getClass().getDeclaredMethod("setTitle", CharSequence.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        Animation.firework(view, setMethod, text, color, startDelay, duration);
    }

    private static void firework(final Object view, final Method setMethod, final String text, int color, long startDelay, long duration) {

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
        valueAnimator.setInterpolator(new AccelerateInterpolator());
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
