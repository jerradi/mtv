package com.jamaal.exoplayer2example;


import android.app.SearchManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.jamaal.exoplayer2example.helpers.Builder;
import com.jamaal.exoplayer2example.helpers.ChannelsAdapter;
import com.jamaal.exoplayer2example.model.Channel;
import com.jamaal.exoplayer2example.model.ChannelDao;
import com.jamaal.exoplayer2example.model.DaoSession;

import org.greenrobot.greendao.query.Query;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListOfItems extends AppCompatActivity implements
        SearchView.OnQueryTextListener {

    private RecyclerView recyclerView;
    private ChannelsAdapter mAdapter;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    return true;
                case R.id.navigation_settings:
                    return true;
            }
            return false;
        }
    };
    private List<Channel> channelList;
    private ChannelDao channelDao;
    private Query<Channel> channelsQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_items);
        recyclerView = findViewById(R.id.rvChannels);
          channelList = new ArrayList<>();
        mAdapter = new ChannelsAdapter(channelList, this);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        prepareChannelData( );

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        DaoSession daoSession = ((App) getApplication()).getDaoSession();
         channelDao = daoSession.getChannelDao ();

        // query all notes, sorted a-z by their text
        channelsQuery = channelDao.queryBuilder().orderAsc(ChannelDao.Properties.Id).build();
    }

    private void prepareChannelData( ) {
        class Loader extends AsyncTask<Void,Void,List<Channel>> {

            @Override
            protected List<Channel> doInBackground(Void... voids) {
                try {

                    List<Channel> channelList =    Builder.getNetworkHelper().getAll().execute().body();
                    channelDao.  saveInTx(channelList);
                    return  channelsQuery.forCurrentThread().list();
                } catch (ConnectException e)  {
                    return  channelsQuery.forCurrentThread().list();
                } catch (Exception e)  {
                    e.printStackTrace();
                  return   Arrays.asList(Channel.mock() ,  Channel.mock() ,Channel.mock() ,Channel.mock());
                }

            }

            @Override
            protected void onPostExecute(List<Channel> channels) {
              channelList  .addAll(channels);
               mAdapter.notifyDataSetChanged();
            }
        }
        new Loader().execute();

 
    }

    private String readFromfile(String fileName, Context context) {
        StringBuilder returnString = new StringBuilder();
        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;
        try {
            fIn = context.getResources().getAssets()
                    .open(fileName, Context.MODE_WORLD_READABLE);
            isr = new InputStreamReader(fIn);
            input = new BufferedReader(isr);
            String line = "";
            while ((line = input.readLine()) != null) {
                returnString.append(line);
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                if (isr != null)
                    isr.close();
                if (fIn != null)
                    fIn.close();
                if (input != null)
                    input.close();
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
        return returnString.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setSearchableInfo(searchManager. getSearchableInfo(this.getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.i("TextSubmit filter by ", "" + query);
        return false;
    }
    @Override
    public boolean onQueryTextChange(String newText) {
        // friendListAdapter.getFilter().filter(newText);
        Log.i("TextChange filter by ", "" + newText);
        mAdapter.getFilter().filter(newText.toString());
        return true;
    }
}