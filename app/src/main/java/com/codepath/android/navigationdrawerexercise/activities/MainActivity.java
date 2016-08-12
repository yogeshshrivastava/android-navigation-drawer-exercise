package com.codepath.android.navigationdrawerexercise.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.codepath.android.navigationdrawerexercise.R;
import com.codepath.android.navigationdrawerexercise.fragments.FamilyGuyFragment;
import com.codepath.android.navigationdrawerexercise.fragments.FuturamaFragment;
import com.codepath.android.navigationdrawerexercise.fragments.SimpsonsFragment;
import com.codepath.android.navigationdrawerexercise.fragments.SouthParkFragment;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView mNavigationView;
    private MenuItem prevMenuSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the toolbar view inside the activity layout
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle("");

        mNavigationView = (NavigationView) findViewById(R.id.nvView);
        mNavigationView.setNavigationItemSelectedListener(this);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = setupDrawerToggle();

        if(savedInstanceState == null) {
            setInitialState();
        }

    }

    private void setInitialState() {
        mNavigationView.getMenu().getItem(0).setChecked(true);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, new SouthParkFragment()).commit();
        getSupportActionBar().setTitle(R.string.south_park);
        prevMenuSelected = mNavigationView.getMenu().getItem(0);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        selectDrawerItem(item);
        return true;
    }

    private void selectDrawerItem(MenuItem item) {
        Class fragmentClass;
        switch(item.getItemId()) {
            case R.id.action_family_guy:
                fragmentClass = FamilyGuyFragment.class;
                break;
            case R.id.action_simpsons:
                fragmentClass = SimpsonsFragment.class;
                break;
            case R.id.action_futurama:
                fragmentClass = FuturamaFragment.class;
                break;
            case R.id.action_south_park:
            default:
                fragmentClass = SouthParkFragment.class;
        }

        try {
            replaceFragment((Fragment) fragmentClass.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setMenuChecked(item);
        getSupportActionBar().setTitle(item.getTitle());
        mDrawer.closeDrawers();
    }

    private void setMenuChecked(MenuItem item) {
        if(prevMenuSelected != null) {
            prevMenuSelected.setChecked(false);
        }
        item.setChecked(true);
        prevMenuSelected = item;
    }


    private void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.flContent, fragment).commit();
    }


}
