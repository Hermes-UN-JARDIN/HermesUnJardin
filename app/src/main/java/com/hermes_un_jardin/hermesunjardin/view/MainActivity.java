package com.hermes_un_jardin.hermesunjardin.view;

import android.app.ActionBar;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.hermes_un_jardin.hermesunjardin.HermesUnJardin;
import com.hermes_un_jardin.hermesunjardin.R;
import com.hermes_un_jardin.hermesunjardin.model.Idea;
import com.hermes_un_jardin.hermesunjardin.presenter.MainPresenter;
import com.hermes_un_jardin.hermesunjardin.presenter.MainPresenterImpl;
import com.hermes_un_jardin.hermesunjardin.utils.Animation;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends FragmentActivity implements MainActivityView {

    public static final String TAG = "MainActivity";
    MainPresenter mPresenter;
    private ActionBar mActionBar;
    private ViewPager mMainBoard;
    private Map<Integer, Fragment> mIdFragment = new HashMap<>();
    private Menu mMenu;
    private boolean mIsDrawerOpen = false;
    // Drawer
    private NavDrawer mDrawer;

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
        mPresenter = new MainPresenterImpl(this, null);
    }
    private void initView() {
        mActionBar = getActionBar();
        mDrawer = (NavDrawer) findViewById(R.id.nav_drawer);
        mMainBoard = (ViewPager) findViewById(R.id.main_board);

        //
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(true);

        mMainBoard.setOffscreenPageLimit(HermesUnJardin.IDEA_DETAIL_COUNT);
        mMainBoard.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return HermesUnJardin.IDEA_DETAIL_COUNT;
            }

            @Override
            public Fragment getItem(int position) {
                Log.d(TAG, String.format("`getItem(%d)", position));

                PictureTextFragment fragment = new PictureTextFragment();
                fragment.init(MainActivity.this);
                mIdFragment.put(position, fragment);

                return fragment;
            }
        });
        mMainBoard.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                Log.d(TAG, String.format("`onPageSelected` %d", position));
            }
        });

        new View(this).postDelayed(new Runnable() {
            @Override
            public void run() {
                setViewLayout();
                showDrawer(false);
            }
        }, 100);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mMenu = menu;
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
                mPresenter.onNavigation(mIsDrawerOpen);
                mIsDrawerOpen = !mIsDrawerOpen;
                break;

            // State: View
            case R.id.menu_edit:
                mPresenter.onMenuEdit();
                break;
            case R.id.menu_drop:
                break;
            case R.id.menu_share:
                break;

            // State: Edit
            case R.id.menu_save:
                mPresenter.onMenuSave();
                break;
            case R.id.menu_cancel:
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showDrawer(boolean show) {
        float x = 0;
        if (show) {
            // To open
            x = 0;
        } else {
            // To close
            x = -mDrawer.getWidth();
        }
        mDrawer.animate().x(x).setDuration(300).setInterpolator(new AccelerateDecelerateInterpolator()).start();
    }

    //
    @Override
    public void selectIdea(Idea idea) {
        // Change title
        double r = Math.random();
        if (r <= 0.5) {
            Animation.firework(mActionBar, idea.getName(), Color.WHITE, 100, 1000);
        } else {
            Animation.blur(mActionBar, idea.getName(), 15, 0.1, 100, 800);
        }

        // Fill idea data
        // Change fragment
        for (int id : mIdFragment.keySet()) {
            PictureTextFragment fragment = (PictureTextFragment) mIdFragment.get(id);
            Idea.Detail detail = idea.getDetail(id);

            if (detail != null) {
                fragment.setImage(BitmapFactory.decodeFile(detail.getPicPath()));
                fragment.setDesc(detail.getDesc());
            } else {
                fragment.setImage(null);
                fragment.setDesc(null);
            }
        }
    }

    // Change state
    @Override
    public void setViewLayout() {
        changeState(State.View);
    }

    @Override
    public void setEditLayout() {
        changeState(State.Edit);
    }

    private void changeState(State state) {
        // Change MainActivity state
        MenuItem share = mMenu.findItem(R.id.menu_share);
        MenuItem edit = mMenu.findItem(R.id.menu_edit);
        MenuItem drop = mMenu.findItem(R.id.menu_drop);
        MenuItem save = mMenu.findItem(R.id.menu_save);
        MenuItem cancel = mMenu.findItem(R.id.menu_cancel);
        switch (state) {
            case Default:
                break;

            case View:
                // Menu
                share.setVisible(true);
                edit.setVisible(true);
                drop.setVisible(true);
                save.setVisible(false);
                cancel.setVisible(false);
                break;

            case Edit:
                share.setVisible(false);
                edit.setVisible(false);
                drop.setVisible(false);
                save.setVisible(true);
                cancel.setVisible(true);
                break;

            default:
                break;
        }

        // Change Fragment state
        for (Fragment fragment : mIdFragment.values()) {
            ((PictureTextFragment) fragment).setState(state);
            Log.d(TAG, fragment.toString());
        }
    }

    //
    public void onEditPic(Fragment fragment) {
        int detailId = getDetailId(fragment);
        mPresenter.onEditPicFromCamera(detailId);
    }

    private int getDetailId(Fragment fragment) {
        for (Integer id : mIdFragment.keySet()) {
            Fragment f = mIdFragment.get(id);
            if (f == fragment) {
                return id;
            }
        }

        return -1;
    }
    public enum State {
        Default,
        View,
        Edit,
    }
}
