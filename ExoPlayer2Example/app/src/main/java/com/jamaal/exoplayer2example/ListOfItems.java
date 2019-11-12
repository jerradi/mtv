package com.jamaal.exoplayer2example;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.jamaal.exoplayer2example.fragments.FavFragment;
import com.jamaal.exoplayer2example.fragments.MainFragment;
import com.jamaal.exoplayer2example.helpers.ChannelsAdapter;
import com.jamaal.exoplayer2example.model.Channel;
import com.jamaal.exoplayer2example.model.ChannelDao;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

public class ListOfItems extends AppCompatActivity
       {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    loadFragment(MainFragment.getInstance(1));
                    return true;
                case R.id.navigation_dashboard:
                    loadFragment(MainFragment.getInstance(2));
                    return true;
                case R.id.navigation_notifications:
                    loadFragment(MainFragment.getInstance(3));
                    return true;
                case R.id.navigation_settings:
                    loadFragment(FavFragment.getInstance(4));
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_items);
        loadFragment(MainFragment.getInstance(12));
        BottomNavigationView bottomNavigationView= findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }





        private void loadFragment(Fragment fragment) {
            // load fragment
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
}