package com.easySMS.ui.acitivity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.easySMS.R;
/**
 * Created by poiuyt on 8/4/16.
 */

public class AfterSignInBaseActivity extends BaseActivity {
    public CoordinatorLayout coordinatorLayout;
    public DrawerLayout mDrawerLayout;
    TextView tvUserName;
    public NavigationView navigationView;
    //    public TextView tvName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        initComponent();
    }

    public void initComponent() {
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_base);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawable_layout);
        navigationView = (NavigationView) findViewById(R.id.na);
        View header = LayoutInflater.from(this).inflate(R.layout.nav_header, null);
        navigationView.addHeaderView(header);
        tvUserName = (TextView) header.findViewById(R.id.tvUserName);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

    }

    private void setupDrawerContent(final NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setCheckable(true);
                        menuItem.setChecked(true);
                        Log.d("Trang", menuItem.getItemId() + "");
                        mDrawerLayout.closeDrawers();
                        switch (menuItem.getItemId()) {
                            case R.id.allMember:
                                setViewPager(0);
                                break;
                            case R.id.yourFriends:
                                setViewPager(1);
                                break;
                            case R.id.chat:
                                setViewPager(2);
                                break;
                            case R.id.signout:
                                signOut();
                                break;
                            default:
                                return true;
                        }
                        return true;

                    }
                }
        );
    }


    void setViewPager(int i) {
    }

    public void signOut() {

    }


    void openNavigation() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }
}