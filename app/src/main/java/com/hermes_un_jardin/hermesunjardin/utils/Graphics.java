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

        // DST
        Bitmap roundedBitmap = bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(roundedBitmap);
        canvas.drawCircle(width / 2, width / 2, radius, new Paint());

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        // SRC
        canvas.drawBitmap(bitmap, 0, 0, paint);

        return roundedBitmap;
    }
}
