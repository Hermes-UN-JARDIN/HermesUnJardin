package com.hermes_un_jardin.hermesunjardin.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.hermes_un_jardin.hermesunjardin.R;

/**
 * Created by songdeming on 2015/5/13.
 */
public class NavDrawer extends FrameLayout {

    public static final String TAG = "NavDrawer";

    private Context mContext;
    private ListView mList;

    public NavDrawer(Context context) {
        this(context, null);
    }

    public NavDrawer(Context context, AttributeSet attrs) {
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
        View root = LayoutInflater.from(mContext).inflate(R.layout.nav_drawer, this);
        mList = (ListView) root.findViewById(R.id.list);

        //
        mList.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, new String[]{"1", "2", "3"}));
    }
}
