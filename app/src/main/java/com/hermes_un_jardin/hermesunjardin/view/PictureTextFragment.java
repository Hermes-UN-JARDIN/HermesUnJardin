package com.hermes_un_jardin.hermesunjardin.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hermes_un_jardin.hermesunjardin.R;

/**
 * Created by songdeming on 2015/5/13.
 */
public class PictureTextFragment extends Fragment {

    public static final String TAG = "PictureTextFragment";

    private ImageView mPicture;
    private TextView mText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        //
        View root = inflater.inflate(R.layout.picture_text_fragment, container, false);
        mPicture = (ImageView) root.findViewById(R.id.picture);
//        mText = (TextView) root.findViewById(R.id.text);

        //
//        mText.setMovementMethod(ScrollingMovementMethod.getInstance());

        return root;
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
