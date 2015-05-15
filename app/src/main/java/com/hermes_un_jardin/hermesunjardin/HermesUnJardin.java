package com.hermes_un_jardin.hermesunjardin;

import android.app.Application;

/**
 * Created by songdeming on 2015/5/15.
 */
public class HermesUnJardin extends Application {

    public static final int IDEA_DETAIL_COUNT = 5;
    private static Application mApplication;

    public static Application getApplication() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mApplication = this;
    }
}
