package com.hermes_un_jardin.hermesunjardin.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.hermes_un_jardin.hermesunjardin.R;

/**
 * Created by songdeming on 2015/5/13.
 */
public class SlideBarView extends FrameLayout {

    public static final String TAG = "SlideBarView";

    private Context mContext;

    public SlideBarView(Context context) {
        this(context, null);
    }

    public SlideBarView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;

        init();
    }

    public void init() {
        initData();
        initView();
    }

    private void initData() {

    }

    private void initView() {
        View root = LayoutInflater.from(mContext).inflate(R.layout.slide_bar_view, this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);


        return true;
    }
}
