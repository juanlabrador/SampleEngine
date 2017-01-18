package com.example.juanlabrador.sampleengine;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.juanlabrador.myapplication.backend.endpoints.quoteApi.model.Quote;
import java.util.List;

/**
 * Created by juanlabrador on 5/01/17.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.CustomViewHolder> {

    private List<Quote> feedItemList;
    private MainActivity mContext;

    public Adapter(Context context, List<Quote> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = (MainActivity) context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_item_quote, null);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, final int position) {
        final Quote feedItem = feedItemList.get(position);
        customViewHolder.setWho(feedItem.getWho());
        customViewHolder.setWhat(feedItem.getWhat());
        customViewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.updatedQuote(feedItem);
            }
        });

        customViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeleteAsyncTask(mContext).execute(feedItem.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return feedItemList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView who;
        TextView what;
        Button delete;
        View view;

        public CustomViewHolder(View view) {
            super(view);
            this.view = view;

            who = (TextView) view.findViewById(R.id.who);
            what = (TextView) view.findViewById(R.id.what);
            delete = (Button) view.findViewById(R.id.delete);
        }

        public void setWho(String who) {
            this.who.setText(who);
        }

        public void setWhat(String what) {
            this.what.setText(what);
        }

        public void setOnClickListener(View.OnClickListener onClickListener) {
            view.setOnClickListener(onClickListener);
        }
    }
}