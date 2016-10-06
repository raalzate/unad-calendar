package com.espaciounido.unadcalendar.dashboard;


import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.espaciounido.unadcalendar.R;
import com.espaciounido.unadcalendar.dashboard.domain.model.ItemEvent;
import com.espaciounido.unadcalendar.utils.Utils;

import java.util.List;


public class EventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ItemEvent> items;
    private OnClickEventListener onListener;

    public EventAdapter(List<ItemEvent> items, OnClickEventListener onListener) {
        this.items = items;
        this.onListener = onListener;
    }

    public void addAll(List<ItemEvent> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).isHeader ? 1 : 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_home_item, parent, false);
            return new ItemEventViewHolder(view, onListener);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_home_title, parent, false);
            return new HeaderViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ItemEventViewHolder) {
            ItemEventViewHolder viewHolder = (ItemEventViewHolder) holder;
            viewHolder.mItem = items.get(position);


            String days;
            if(viewHolder.mItem.day == 0) {
                days = "0d";
            } else {
                days = String.format("-%sd", viewHolder.mItem.day);
            }

            int colorDefine = Utils.defineColorBayDay(viewHolder.mItem.day);
            if(colorDefine != Color.WHITE) {
                TextDrawable drawable =
                        TextDrawable.builder()
                                .beginConfig()
                                .withBorder(4)
                                .textColor(Color.WHITE)
                                .endConfig()
                                .buildRoundRect(days, colorDefine, 10);
                viewHolder.countdown.setImageDrawable(drawable);
            } else {
                viewHolder.countdown.setVisibility(View.GONE);
            }

            viewHolder.numberProgressBar.setProgress(viewHolder.mItem.progress);
            viewHolder.title.setText(viewHolder.mItem.title);
            viewHolder.detail.setText(viewHolder.mItem.detail);

        }

        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.title.setText(items.get(position).title);
        }
    }



    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnClickEventListener {
        void onClickEvent(ItemEvent item);
    }

    public class ItemEventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final TextView title;
        public final TextView detail;
        public final NumberProgressBar numberProgressBar;
        private final ImageView countdown;
        private final OnClickEventListener onClickEventListener;

        public ItemEvent mItem;

        public ItemEventViewHolder(View view, OnClickEventListener onListener) {
            super(view);
            mView = view;
            title = (TextView) view.findViewById(R.id.title);
            detail = (TextView) view.findViewById(R.id.detail);
            countdown = (ImageView) view.findViewById(R.id.countdown);
            numberProgressBar = (NumberProgressBar) view.findViewById(R.id.number_progress_bar);
            onClickEventListener = onListener;
            mView.setOnClickListener(this);

        }

        @Override
        public String toString() {
            return super.toString();
        }

        @Override
        public void onClick(View view) {
            if (onClickEventListener != null) {
                onClickEventListener.onClickEvent(mItem);
            }
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView title;

        public HeaderViewHolder(View view) {
            super(view);
            mView = view;
            title = (TextView) view.findViewById(R.id.title);

        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
