package com.hermes_un_jardin.hermesunjardin;

import android.app.ActionBar;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.hermes_un_jardin.hermesunjardin.controller.Idea;
import com.hermes_un_jardin.hermesunjardin.view.NavDrawer;
import com.hermes_un_jardin.hermesunjardin.view.PictureTextFragment;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends FragmentActivity {

    private ActionBar mActionBar;
    private NavDrawer mDrawer;
    private boolean mIsDrawerOpen = false;
    private ViewPager mMainBoard;
    private Map<Integer, Fragment> mIdFragment = new HashMap<Integer, Fragment>();
    private Fragment mCurrentFragment;
    private Idea mIdea;
    private State mState = State.Default;
    private Menu mMenu;

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
                return HermesUnJardin.IDEA_DETAIL_COUNT;
            }

            @Override
            public Fragment getItem(int position) {
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

        for (int i = 0; i < Math.min(mIdea.getDetailList().size(), HermesUnJardin.IDEA_DETAIL_COUNT); i++) {
            Idea.Detail detail = mIdea.getDetailList().get(i);

            PictureTextFragment pictureTextFragment = (PictureTextFragment) mIdFragment.get(i);
            pictureTextFragment.setImage(BitmapFactory.decodeFile(detail.getPicPath()));
            pictureTextFragment.setDesc(detail.getDesc());
        }

        mMainBoard.setCurrentItem(0);
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

        MenuItem share = mMenu.findItem(R.id.menu_share);
        MenuItem edit = mMenu.findItem(R.id.menu_edit);
        MenuItem drop = mMenu.findItem(R.id.menu_drop);
        MenuItem save = mMenu.findItem(R.id.menu_save);
        MenuItem cancel = mMenu.findItem(R.id.menu_cancel);
        switch (state) {
            case Default:
                break;

            case View:
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

        ((PictureTextFragment) mCurrentFragment).setState(state);
        mState = state;
    }

    public void onClickSelectPic(View v) {
        ((PictureTextFragment) mCurrentFragment).onClickSelectPic(v);
    }

    public static enum State {
        Default,
        View,
        Edit,
    }
}
