package com.hermes_un_jardin.hermesunjardin.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

/**
 * Created by songdeming on 2015/5/19.
 */
public class Graphics {
    public static final String TAG = "utils";

    /**
     * @param bitmap should `getWidth() == getHeight()`.
     * @return
     */
    public static Bitmap createRoundedBitmap(Bitmap bitmap, int radius) {
        final int width = bitmap.getWidth();

        // DST. Original image.
        Bitmap dstBitmap = bitmap;
        // SRC. Circle.
        Bitmap srcBitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        {
            Canvas canvas = new Canvas(srcBitmap);
            canvas.drawCircle(width / 2, width / 2, radius, new Paint(Paint.ANTI_ALIAS_FLAG));
        }

        // Xfer.
        Bitmap roundedBitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(roundedBitmap);
        canvas.drawBitmap(dstBitmap, 0, 0, null);
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(srcBitmap, 0, 0, paint);

        return roundedBitmap;
    }
}
