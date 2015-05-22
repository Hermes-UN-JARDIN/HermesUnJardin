package com.hermes_un_jardin.hermesunjardin.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hermes_un_jardin.hermesunjardin.MainActivity;
import com.hermes_un_jardin.hermesunjardin.R;

/**
 * Created by songdeming on 2015/5/13.
 */
public class PictureTextFragment extends Fragment {

    public static final String TAG = "PictureTextFragment";

    private ImageView mImage;
    private TextView mText;

    private GridLayout mSelectPic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        //
        View root = inflater.inflate(R.layout.picture_text_fragment, container, false);
        mImage = (ImageView) root.findViewById(R.id.picture);
        mText = (TextView) root.findViewById(R.id.text);
        mSelectPic = (GridLayout) root.findViewById(R.id.select_pic);

        //
        mText.setMovementMethod(ScrollingMovementMethod.getInstance());

        return root;
    }

    public void setImage(Bitmap bitmap) {
        mImage.setImageBitmap(bitmap);
    }

    public void setDesc(String desc) {
        mText.setText(desc);
    }

    public void setState(MainActivity.State state) {
        switch (state) {
            case Default:
                break;

            case View:
                mSelectPic.setVisibility(View.INVISIBLE);
                mText.setEnabled(false);
                break;

            case Edit:
                mSelectPic.setVisibility(View.VISIBLE);
                mText.setEnabled(true);
                break;

            default:
                break;
        }
    }

    public void onClickSelectPic(View v) {
        switch (v.getId()) {
            case R.id.camera:
                break;

            default:
                break;
        }
    }
}
