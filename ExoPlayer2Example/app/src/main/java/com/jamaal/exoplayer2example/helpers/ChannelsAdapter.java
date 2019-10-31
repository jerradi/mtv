package com.jamaal.exoplayer2example.helpers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.jamaal.exoplayer2example.PlayerActivity;
import com.squareup.picasso.Picasso;
import com.jamaal.exoplayer2example.ListOfItems;
import com.jamaal.exoplayer2example.R;
import com.jamaal.exoplayer2example.model.Channel;

import java.util.ArrayList;
import java.util.List;

public class ChannelsAdapter extends RecyclerView .Adapter<ChannelsAdapter.MyViewHolder> implements Filterable {

    private List<Channel> channelsList;
    private ListOfItems activity;
    private ChannelFilter channelFilter;
    private Typeface typeface;

    private List<Channel> filteredList;
    private Context ctx;

    @Override
    public Filter getFilter() {
        if (channelFilter == null) {
            channelFilter = new ChannelFilter();
        }

        return channelFilter;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle, tvLanguage;
        ImageView ivLogo;

        public MyViewHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvLanguage = (TextView) view.findViewById(R.id.tvLanguage);
            ivLogo = (ImageView) view.findViewById(R.id.ivLogo);
        }
    }


    public ChannelsAdapter(List<Channel> ChannelsList, Context ctxx) {
        this.channelsList = ChannelsList;
        this.filteredList = ChannelsList;
        ctx = ctxx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final Channel channel = channelsList.get(position);
        holder.tvTitle.setText(channel.getName());
        holder.tvLanguage.setText(channel.getLanguage());
        Picasso.get().load(channel.getImg()).into(holder.ivLogo);
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctx, PlayerActivity.class);
                i.putExtra("url", channel.getUri());
                ctx.startActivity(i);
            }
        });
        if(channel.getCategory() ==0)  holder.ivLogo.setAlpha(0.1f);
    }

    @Override
    public int getItemCount() {
        return channelsList.size();
    }

    private class ChannelFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<Channel> tempList = new ArrayList<Channel>();

                // search content in friend list
                for (Channel user : filteredList) {
                    try {
                        if (user.getName()!=null && user.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                            tempList.add(user);

                        }
                    }catch (Exception e) {
                        Log.i("FILTER", "performFiltering: " + e.getLocalizedMessage());
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = filteredList.size();
                filterResults.values = filteredList;
            }

            return filterResults;
        }

        /**
         * Notify about filtered list to ui
         *
         * @param constraint text
         * @param results    filtered result
         */
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            channelsList = (List<Channel>) results.values;
            notifyDataSetChanged();
        }
    }

}