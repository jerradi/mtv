package com.jamaal.exoplayer2example.fragments;
import android.app.SearchManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jamaal.exoplayer2example.App;
import com.jamaal.exoplayer2example.R;
import com.jamaal.exoplayer2example.helpers.Builder;
import com.jamaal.exoplayer2example.helpers.ChannelsAdapter;
import com.jamaal.exoplayer2example.model.Channel;
import com.jamaal.exoplayer2example.model.ChannelDao;
import com.jamaal.exoplayer2example.model.DaoSession;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavFragment extends Fragment   {
private static FavFragment instance=null;

    private RecyclerView recyclerView;
    private ChannelsAdapter mAdapter;
    int id = -1;
   public static FavFragment getInstance(int di){

           instance = new FavFragment();

    return  instance;
}
    public FavFragment() {
   id=(int)(Math.random()*233333);
   }


    private List<Channel> channelList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ChannelDao channelDao;
    private Query<Channel> channelsQuery;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {
        // Inflate the layout for this fragment


        View mView = inflater.inflate(R.layout.main_fragement, container, false);


        super.onCreate(savedInstanceState); 
        recyclerView = (RecyclerView) mView.findViewById(R.id.rvChannels);
        channelList = new ArrayList<>();
        mAdapter = new ChannelsAdapter(channelList, getActivity());
        final LinearLayoutManager layoutParams = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutParams);
        ((AppCompatActivity)getActivity()).setSupportActionBar((Toolbar) mView.findViewById(R.id.toolbar));
        setHasOptionsMenu(true);

        mSwipeRefreshLayout = mView.findViewById(R.id.swipeRefreshLayout);
        TextView tvId =  mView.findViewById(R.id.tvid);
        tvId.setText("HELLOO "+id);
        recyclerView.setLayoutManager(new GridLayoutManager(mView.getContext(), 2, GridLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                prepareChannelData();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });


        DaoSession daoSession = ((App)  getActivity().getApplication()).getDaoSession();
        channelDao = daoSession.getChannelDao ();

        // query all notes, sorted a-z by their text
        channelsQuery = channelDao.queryBuilder().where(ChannelDao.Properties.Category.eq(2)).orderAsc(ChannelDao.Properties.Id).build();
        prepareChannelData( );

        return  mView;
    }

    private void prepareChannelData( ) {
        class Loader extends AsyncTask<Void,Void,List<Channel>> {

            @Override
            protected List<Channel> doInBackground(Void... voids) {
                try {

                   // List<Channel> channelList =    Builder.getNetworkHelper().getAll().execute().body();
                    //channelDao.  saveInTx(channelList);
                    return  channelsQuery.forCurrentThread().list();
                } catch (Exception e) {
                    e.printStackTrace();
                    return channelsQuery.forCurrentThread().list();
                }

            }

            @Override
            protected void onPostExecute(List<Channel> channels) {
                channelList  .addAll(channels);
                mAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }
        mSwipeRefreshLayout.setRefreshing(true);
        new Loader().execute();


    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.fav_menu, menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_refresh:
                prepareChannelData();
                break;

        }
        return super.onOptionsItemSelected(item);
    }


}
