package com.hermes_un_jardin.hermesunjardin;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.hermes_un_jardin.hermesunjardin.view.NavDrawer;
import com.hermes_un_jardin.hermesunjardin.view.PictureTextFragment;


public class MainActivity extends FragmentActivity {

    private ActionBar mActionBar;
    private NavDrawer mDrawer;
    private boolean mIsDrawerOpen = false;
    private ViewPager mMainBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public void init() {
        initData();
        initView();
    }

    private void initData() {
    }

    private void initView() {
        mActionBar = getActionBar();
        mDrawer = (NavDrawer) findViewById(R.id.nav_drawer);
        mMainBoard = (ViewPager) findViewById(R.id.main_board);

        //
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(true);

        mMainBoard.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new PictureTextFragment();

                    case 1:
                        return new PictureTextFragment();

                    case 2:
                        return new PictureTextFragment();

                    case 3:
                        return new PictureTextFragment();

                    case 4:
                        return new PictureTextFragment();

                    default:
                        return null;
                }
            }
        });

        //
        mDrawer.post(new Runnable() {
            @Override
            public void run() {
                mDrawer.setX(-mDrawer.getWidth());
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                float x = 0;
                if (mIsDrawerOpen) {
                    // To close
                    x = -mDrawer.getWidth();
                } else {
                    // To open
                    x = 0;
                }
                mDrawer.animate().x(x).setDuration(300).setInterpolator(new AccelerateDecelerateInterpolator()).start();
                mIsDrawerOpen = !mIsDrawerOpen;
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
