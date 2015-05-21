package com.hermes_un_jardin.hermesunjardin.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hermes_un_jardin.hermesunjardin.HermesUnJardin;
import com.hermes_un_jardin.hermesunjardin.R;
import com.hermes_un_jardin.hermesunjardin.controller.Idea;
import com.hermes_un_jardin.hermesunjardin.utils.Graphics;

/**
 * Created by songdeming on 2015/5/13.
 */
public class NavDrawer extends FrameLayout {

    public static final String TAG = "NavDrawer";

    private Context mContext;
    private ListView mList;
    private DrawerListAdapter mListAdapter;

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
        mListAdapter = new DrawerListAdapter(mContext);
        for (String name : HermesUnJardin.getApplication().getFilesDir().list()) {
            mListAdapter.add(Idea.readFrom(name));
        }
    }

    private void initView() {
        View root = LayoutInflater.from(mContext).inflate(R.layout.nav_drawer, this);
        mList = (ListView) root.findViewById(R.id.list);

        //
        mList.setAdapter(mListAdapter);
    }

    private class DrawerListAdapter extends ArrayAdapter<Idea> {

        public DrawerListAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final Idea idea = mListAdapter.getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(HermesUnJardin.getApplication()).inflate(R.layout.drawer_list_item_view, null);
            }

            RelativeLayout itemRoot = (RelativeLayout) convertView;
            TextView txt = (TextView) itemRoot.findViewById(R.id.text);
            ImageView img = (ImageView) itemRoot.findViewById(R.id.icon);

            txt.setText(idea.getName());
            Bitmap bitmap = BitmapFactory.decodeFile(idea.getIconPath());
            img.setImageBitmap(Graphics.createRoundedBitmap(bitmap, (int) (bitmap.getWidth() * 0.35)));

            // `onClick` event
            itemRoot.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(HermesUnJardin.getApplication(), String.format("Idea(%s) has been clicked", idea.getName()), Toast.LENGTH_SHORT).show();
                }
            });

            return convertView;
        }
    }
}
