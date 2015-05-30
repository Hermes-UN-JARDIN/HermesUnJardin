package com.hermes_un_jardin.hermesunjardin;

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

import com.hermes_un_jardin.hermesunjardin.controller.Idea;
import com.hermes_un_jardin.hermesunjardin.utils.Animation;
import com.hermes_un_jardin.hermesunjardin.view.NavDrawer;
import com.hermes_un_jardin.hermesunjardin.view.PictureTextFragment;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends FragmentActivity {

    public static final String TAG = "MainActivity";

    private ActionBar mActionBar;
    private NavDrawer mDrawer;
    private boolean mIsDrawerOpen = false;
    private ViewPager mMainBoard;
    private Map<Integer, Fragment> mIdFragment = new HashMap<Integer, Fragment>();
    private Fragment mCurrentFragment;
    private Idea mIdea;
    private State mState = State.Default;
    private Menu mMenu;

    public State getState() {
        return mState;
    }

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

        mMainBoard.setOffscreenPageLimit(HermesUnJardin.IDEA_DETAIL_COUNT);
        mMainBoard.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return HermesUnJardin.IDEA_DETAIL_COUNT;
            }

            @Override
            public Fragment getItem(int position) {
                Log.d(TAG, String.format("`getItem(%d)", position));

                switch (position) {
                    case 0:
                        mCurrentFragment = new PictureTextFragment();
                        break;

                    case 1:
                        mCurrentFragment = new PictureTextFragment();
                        break;

                    case 2:
                        mCurrentFragment = new PictureTextFragment();
                        break;

                    case 3:
                        mCurrentFragment = new PictureTextFragment();
                        break;

                    case 4:
                        mCurrentFragment = new PictureTextFragment();
                        break;

                    default:
                        mCurrentFragment = null;
                        break;
                }

                mIdFragment.put(position, mCurrentFragment);
                return mCurrentFragment;
            }
        });
        mMainBoard.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                Log.d(TAG, String.format("`onPageSelected` %d", position));
            }
        });

        new View(this).post(new Runnable() {
            @Override
            public void run() {
                // Close Drawer
                mDrawer.setX(-mDrawer.getWidth());

                // Init 'Setting Button' in Action Bar.
                changeState(State.View);
            }
        });
    }

    /**
     * Using 'idea' to fill the activity.
     *
     * @param idea
     */
    public void setIdea(Idea idea) {
        mIdea = idea;

        // Change fragment
        for (int id : mIdFragment.keySet()) {
            PictureTextFragment fragment = (PictureTextFragment) mIdFragment.get(id);
            Idea.Detail detail = mIdea.getDetail(id);

            if (detail != null) {
                fragment.setImage(BitmapFactory.decodeFile(detail.getPicPath()));
                fragment.setDesc(detail.getDesc());
            } else {
                fragment.setImage(null);
                fragment.setDesc(null);
            }
        }

        // Change title
        Method setTitleMethod = null;
        try {
            setTitleMethod = mActionBar.getClass().getDeclaredMethod("setTitle", CharSequence.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        Animation.foo(mActionBar, setTitleMethod, idea.getName(), Color.WHITE, 0, 1000);
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

            // State: View
            case R.id.menu_edit:
                changeState(State.Edit);
                break;
            case R.id.menu_drop:
                break;
            case R.id.menu_share:
                break;

            // State: Edit
            case R.id.menu_save:
                changeState(State.View);
                break;
            case R.id.menu_cancel:
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void changeState(State state) {

        this.mState = state;

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

    public void onClickSelectPic(View v) {
        ((PictureTextFragment) mCurrentFragment).onClickSelectPic(v);
    }

    public enum State {
        Default,
        View,
        Edit,
    }
}
